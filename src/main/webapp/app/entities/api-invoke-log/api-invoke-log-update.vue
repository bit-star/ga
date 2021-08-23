<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gaApp.apiInvokeLog.home.createOrEditLabel"
          data-cy="ApiInvokeLogCreateUpdateHeading"
          v-text="$t('gaApp.apiInvokeLog.home.createOrEditLabel')"
        >
          Create or edit a ApiInvokeLog
        </h2>
        <div>
          <div class="form-group" v-if="apiInvokeLog.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="apiInvokeLog.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.apiInvokeLog.login')" for="api-invoke-log-login">Login</label>
            <input
              type="text"
              class="form-control"
              name="login"
              id="api-invoke-log-login"
              data-cy="login"
              :class="{ valid: !$v.apiInvokeLog.login.$invalid, invalid: $v.apiInvokeLog.login.$invalid }"
              v-model="$v.apiInvokeLog.login.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.apiInvokeLog.apiName')" for="api-invoke-log-apiName">Api Name</label>
            <input
              type="text"
              class="form-control"
              name="apiName"
              id="api-invoke-log-apiName"
              data-cy="apiName"
              :class="{ valid: !$v.apiInvokeLog.apiName.$invalid, invalid: $v.apiInvokeLog.apiName.$invalid }"
              v-model="$v.apiInvokeLog.apiName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.apiInvokeLog.method')" for="api-invoke-log-method">Method</label>
            <select
              class="form-control"
              name="method"
              :class="{ valid: !$v.apiInvokeLog.method.$invalid, invalid: $v.apiInvokeLog.method.$invalid }"
              v-model="$v.apiInvokeLog.method.$model"
              id="api-invoke-log-method"
              data-cy="method"
            >
              <option value="GET" v-bind:label="$t('gaApp.HttpMethod.GET')">GET</option>
              <option value="HEAD" v-bind:label="$t('gaApp.HttpMethod.HEAD')">HEAD</option>
              <option value="POST" v-bind:label="$t('gaApp.HttpMethod.POST')">POST</option>
              <option value="PUT" v-bind:label="$t('gaApp.HttpMethod.PUT')">PUT</option>
              <option value="PATCH" v-bind:label="$t('gaApp.HttpMethod.PATCH')">PATCH</option>
              <option value="DELETE" v-bind:label="$t('gaApp.HttpMethod.DELETE')">DELETE</option>
              <option value="OPTIONS" v-bind:label="$t('gaApp.HttpMethod.OPTIONS')">OPTIONS</option>
              <option value="TRACE" v-bind:label="$t('gaApp.HttpMethod.TRACE')">TRACE</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.apiInvokeLog.direction')" for="api-invoke-log-direction">Direction</label>
            <select
              class="form-control"
              name="direction"
              :class="{ valid: !$v.apiInvokeLog.direction.$invalid, invalid: $v.apiInvokeLog.direction.$invalid }"
              v-model="$v.apiInvokeLog.direction.$model"
              id="api-invoke-log-direction"
              data-cy="direction"
            >
              <option value="Enter" v-bind:label="$t('gaApp.ApiDirection.Enter')">Enter</option>
              <option value="Out" v-bind:label="$t('gaApp.ApiDirection.Out')">Out</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.apiInvokeLog.httpStatusCode')" for="api-invoke-log-httpStatusCode"
              >Http Status Code</label
            >
            <input
              type="number"
              class="form-control"
              name="httpStatusCode"
              id="api-invoke-log-httpStatusCode"
              data-cy="httpStatusCode"
              :class="{ valid: !$v.apiInvokeLog.httpStatusCode.$invalid, invalid: $v.apiInvokeLog.httpStatusCode.$invalid }"
              v-model.number="$v.apiInvokeLog.httpStatusCode.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.apiInvokeLog.time')" for="api-invoke-log-time">Time</label>
            <div class="d-flex">
              <input
                id="api-invoke-log-time"
                data-cy="time"
                type="datetime-local"
                class="form-control"
                name="time"
                :class="{ valid: !$v.apiInvokeLog.time.$invalid, invalid: $v.apiInvokeLog.time.$invalid }"
                :value="convertDateTimeFromServer($v.apiInvokeLog.time.$model)"
                @change="updateInstantField('time', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.apiInvokeLog.reqeustData')" for="api-invoke-log-reqeustData"
              >Reqeust Data</label
            >
            <textarea
              class="form-control"
              name="reqeustData"
              id="api-invoke-log-reqeustData"
              data-cy="reqeustData"
              :class="{ valid: !$v.apiInvokeLog.reqeustData.$invalid, invalid: $v.apiInvokeLog.reqeustData.$invalid }"
              v-model="$v.apiInvokeLog.reqeustData.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.apiInvokeLog.responseData')" for="api-invoke-log-responseData"
              >Response Data</label
            >
            <textarea
              class="form-control"
              name="responseData"
              id="api-invoke-log-responseData"
              data-cy="responseData"
              :class="{ valid: !$v.apiInvokeLog.responseData.$invalid, invalid: $v.apiInvokeLog.responseData.$invalid }"
              v-model="$v.apiInvokeLog.responseData.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.apiInvokeLog.ok')" for="api-invoke-log-ok">Ok</label>
            <input
              type="checkbox"
              class="form-check"
              name="ok"
              id="api-invoke-log-ok"
              data-cy="ok"
              :class="{ valid: !$v.apiInvokeLog.ok.$invalid, invalid: $v.apiInvokeLog.ok.$invalid }"
              v-model="$v.apiInvokeLog.ok.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.apiInvokeLog.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./api-invoke-log-update.component.ts"></script>
