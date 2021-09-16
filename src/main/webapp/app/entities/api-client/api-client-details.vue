<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="apiClient">
        <h2 class="jh-entity-heading" data-cy="apiClientDetailsHeading">
          <span v-text="$t('gaApp.apiClient.detail.title')">ApiClient</span> {{ apiClient.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('gaApp.apiClient.name')">Name</span>
          </dt>
          <dd>
            <span>{{ apiClient.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.apiClient.apiKey')">Api Key</span>
          </dt>
          <dd>
            <span>{{ apiClient.apiKey }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.apiClient.apiSecret')">Api Secret</span>
          </dt>
          <dd>
            <span>{{ apiClient.apiSecret }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.apiClient.enable')">Enable</span>
          </dt>
          <dd>
            <span>{{ apiClient.enable }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.apiClient.linkSystem')">Link System</span>
          </dt>
          <dd>
            <div v-if="apiClient.linkSystem">
              <router-link :to="{ name: 'LinkSystemView', params: { linkSystemId: apiClient.linkSystem.id } }">{{
                apiClient.linkSystem.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="apiClient.id"
          :to="{ name: 'ApiClientEdit', params: { apiClientId: apiClient.id } }"
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

<script lang="ts" src="./api-client-details.component.ts"></script>
