package com.d5.doc.provider

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.util.IconLoader
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiMethod
import com.ly.doc.builder.ApiDataBuilder
import com.ly.doc.constants.FrameworkEnum
import com.ly.doc.constants.TemplateVariable
import com.ly.doc.model.ApiAllData
import com.ly.doc.model.ApiConfig
import com.ly.doc.model.SourceCodePath
import freemarker.template.Configuration
import freemarker.template.Template
import freemarker.template.TemplateNotFoundException
import java.awt.BorderLayout
import java.io.StringWriter
import javax.swing.Icon
import javax.swing.JEditorPane
import javax.swing.JPanel
import javax.swing.JScrollPane


class MyGutterIconProvider : LineMarkerProvider {

    private var myIcon: Icon = IconLoader.getIcon("/icons/myIcon.svg", MyGutterIconProvider::class.java)
    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? {
        if (element is PsiMethod && isControllerMethod(element)) {
            return LineMarkerInfo(
                    element,
                    element.textRange,
                    myIcon,
                    { "生成 API 文档" }, // 提示文本
                    { _, elt -> // 点击事件处理器
                        val project = elt.project
                        showApiDataPopup(project, elt as PsiMethod)
                    },
                    GutterIconRenderer.Alignment.RIGHT, // 图标对齐方式
                    { "API 文档生成按钮" } // 无障碍访问名称
            )
        }
        return null
    }

    private fun isControllerMethod(method: PsiMethod): Boolean {
        val containingClass = method.containingClass ?: return false
        return containingClass.hasAnnotation("org.springframework.stereotype.Controller") ||
                containingClass.hasAnnotation("org.springframework.web.bind.annotation.RestController")
    }

    private fun showApiDataPopup(project: Project, method: PsiMethod) {
        val config = buildApiConfig(method)

        val apiData = ApiDataBuilder.getApiDataTree(config)
        val renderedMarkdown = renderApiData(apiData, config)
        val htmlContent = convertMarkdownToHtml(renderedMarkdown)

        val editorPane = JEditorPane("text/html", htmlContent)
        editorPane.isEditable = false
        editorPane.background = null

        val panel = JPanel(BorderLayout())
        panel.add(JScrollPane(editorPane), BorderLayout.CENTER)

        val popup = JBPopupFactory.getInstance()
                .createComponentPopupBuilder(panel, null)
                .setTitle("API Documentation")
                .setResizable(true)
                .setMovable(true)
                .setRequestFocus(true)
                .setMinSize(java.awt.Dimension(100, 150)) // 设置初始大小
                .createPopup()

        // 获取当前方法所在文件的 Editor
        val editor = getEditorForMethod(project, method.containingFile)

        // 展示弹窗
        editor?.let { popup.showInBestPositionFor(it) }
    }

    private fun getEditorForMethod(project: Project, psiFile: PsiFile): Editor? {
        // 直接从当前项目获取选中的 Editor
        return FileEditorManager.getInstance(project).selectedTextEditor
    }

    private fun buildApiConfig(method: PsiMethod): ApiConfig {
        val config = ApiConfig()
        println(method.project.name)
        config.projectName = method.project.name
        config.serverUrl = "http://127.0.0.1:8899"
        config.openUrl = "http://localhost:7700/api"
        config.appToken = "be4211613a734b45888c075741680e49"
        config.isAllInOne = false
        val externalSourcePath = SourceCodePath().setPath("src/main/java/com/d5/framework").setDesc("基础包")
        println(method.project.basePath)
        val projectSourcePath = SourceCodePath().setPath(method.project.basePath).setDesc("当前使用插件的项目")
        config.sourceCodePaths = listOf(externalSourcePath, projectSourcePath)
        config.baseDir = method.project.basePath
        config.codePath = "/src/main/java"
        config.packageFilters = method.containingClass?.qualifiedName
        config.methodName = method.name
        config.framework = FrameworkEnum.SPRING.framework
        return config
    }

    private fun renderApiData(apiData: ApiAllData, apiConfig: ApiConfig): String {
        val config = Configuration(Configuration.VERSION_2_3_32)
        val templateStream = this::class.java.classLoader.getResourceAsStream("template/ApiMethodDoc.ftl")
                ?: throw TemplateNotFoundException("Template not found: template/ApiMethodDoc.ftl", "ApiMethodDoc.ftl", null)

        val templateReader = templateStream.reader()
        val template = Template("ApiMethodDoc.ftl", templateReader, config)
        val apiDoc = apiData.apiDocList[0];
        val templateData = mapOf(
                TemplateVariable.DESC.variable to apiDoc.desc,
                TemplateVariable.NAME.variable to apiDoc.name,
                TemplateVariable.LIST.variable to apiDoc.list,
                TemplateVariable.REQUEST_EXAMPLE.variable to apiConfig.isRequestExample,
                TemplateVariable.RESPONSE_EXAMPLE.variable to apiConfig.isResponseExample
        )


        val writer = StringWriter()
        template.process(templateData, writer)
        return writer.toString()
    }

    private fun convertMarkdownToHtml(markdown: String): String {
        val parser = com.vladsch.flexmark.parser.Parser.builder().build()
        val document = parser.parse(markdown)
        val renderer = com.vladsch.flexmark.html.HtmlRenderer.builder().build()
        return renderer.render(document)
    }
}
