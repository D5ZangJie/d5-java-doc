# ${desc}
<#list list as doc>
<#if doc.deprecated>
## ~~${doc.desc?html}~~
<#else>
## ${doc.desc?html}
</#if>

**URL:** `${doc.url}`

**Type:** `${doc.type}`

<#if doc.author?has_content>
**Author:** ${doc.author}
</#if>

**Content-Type:** `${doc.contentType}`

**Description:** ${doc.detail}

<#if doc.headers?has_content>
**Request-headers:**

| Header | Type | Required | Description | Since |
|--------|------|----------|-------------|-------|
<#list doc.headers as header>
${header}
</#list>
</#if>

<#if doc.pathParams?has_content>
**Path-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
<#list doc.pathParams as param>
|${param.field}|${param.type}|${param.required?string('yes', 'no')}|${param.desc?replace("\n", "<br>")}|${param.version}|
</#list>
</#if>

<#if doc.queryParams?has_content>
**Query-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
<#list doc.queryParams as param>
|${param.field}|${param.type}|${param.required?string('yes', 'no')}|${param.desc?replace("\n", "<br>")}|${param.version}|
</#list>
</#if>

<#if doc.requestParams?has_content>
**Body-parameters:**

| Parameter | Type | Required | Description | Since |
|-----------|------|----------|-------------|-------|
<#list doc.requestParams as param>
|${param.field}|${param.type}|${param.required?string('yes', 'no')}|${param.desc?replace("\n", "<br>")}|${param.version}|
</#list>
</#if>

<#if doc.requestUsage?has_content && isRequestExample>
**Request-example:**
```bash
${doc.requestUsage}
```
</#if>

<#if doc.responseParams?has_content>
**Response-fields:**

| Field | Type | Description | Since |
|-------|------|-------------|-------|
<#list doc.responseParams as param>
|${param.field}|${param.type}|${param.desc?replace("\n", "<br>")}|${param.version}|
</#list>
</#if>

<#if doc.responseUsage?has_content && isResponseExample>
**Response-example:**
```json
${doc.responseUsage}
```
</#if>

</#list>
