<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="apiInvokeLog">
        <h2 class="jh-entity-heading" data-cy="apiInvokeLogDetailsHeading">
          <span v-text="$t('gaApp.apiInvokeLog.detail.title')">ApiInvokeLog</span> {{ apiInvokeLog.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('gaApp.apiInvokeLog.login')">Login</span>
          </dt>
          <dd>
            <span>{{ apiInvokeLog.login }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.apiInvokeLog.apiName')">Api Name</span>
          </dt>
          <dd>
            <span>{{ apiInvokeLog.apiName }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.apiInvokeLog.method')">Method</span>
          </dt>
          <dd>
            <span v-text="$t('gaApp.HttpMethod.' + apiInvokeLog.method)">{{ apiInvokeLog.method }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.apiInvokeLog.direction')">Direction</span>
          </dt>
          <dd>
            <span v-text="$t('gaApp.ApiDirection.' + apiInvokeLog.direction)">{{ apiInvokeLog.direction }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.apiInvokeLog.httpStatusCode')">Http Status Code</span>
          </dt>
          <dd>
            <span>{{ apiInvokeLog.httpStatusCode }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.apiInvokeLog.time')">Time</span>
          </dt>
          <dd>
            <span v-if="apiInvokeLog.time">{{ $d(Date.parse(apiInvokeLog.time), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.apiInvokeLog.reqeustData')">Reqeust Data</span>
          </dt>
          <dd>
            <span>{{ apiInvokeLog.reqeustData }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.apiInvokeLog.responseData')">Response Data</span>
          </dt>
          <dd>
            <span>{{ apiInvokeLog.responseData }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.apiInvokeLog.ok')">Ok</span>
          </dt>
          <dd>
            <span>{{ apiInvokeLog.ok }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="apiInvokeLog.id"
          :to="{ name: 'ApiInvokeLogEdit', params: { apiInvokeLogId: apiInvokeLog.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./api-invoke-log-details.component.ts"></script>
