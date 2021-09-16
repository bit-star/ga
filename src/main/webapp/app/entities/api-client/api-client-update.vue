<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gaApp.apiClient.home.createOrEditLabel"
          data-cy="ApiClientCreateUpdateHeading"
          v-text="$t('gaApp.apiClient.home.createOrEditLabel')"
        >
          Create or edit a ApiClient
        </h2>
        <div>
          <div class="form-group" v-if="apiClient.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="apiClient.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.apiClient.name')" for="api-client-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="api-client-name"
              data-cy="name"
              :class="{ valid: !$v.apiClient.name.$invalid, invalid: $v.apiClient.name.$invalid }"
              v-model="$v.apiClient.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.apiClient.apiKey')" for="api-client-apiKey">Api Key</label>
            <input
              type="text"
              class="form-control"
              name="apiKey"
              id="api-client-apiKey"
              data-cy="apiKey"
              :class="{ valid: !$v.apiClient.apiKey.$invalid, invalid: $v.apiClient.apiKey.$invalid }"
              v-model="$v.apiClient.apiKey.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.apiClient.apiSecret')" for="api-client-apiSecret">Api Secret</label>
            <input
              type="text"
              class="form-control"
              name="apiSecret"
              id="api-client-apiSecret"
              data-cy="apiSecret"
              :class="{ valid: !$v.apiClient.apiSecret.$invalid, invalid: $v.apiClient.apiSecret.$invalid }"
              v-model="$v.apiClient.apiSecret.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.apiClient.enable')" for="api-client-enable">Enable</label>
            <input
              type="text"
              class="form-control"
              name="enable"
              id="api-client-enable"
              data-cy="enable"
              :class="{ valid: !$v.apiClient.enable.$invalid, invalid: $v.apiClient.enable.$invalid }"
              v-model="$v.apiClient.enable.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.apiClient.linkSystem')" for="api-client-linkSystem">Link System</label>
            <select class="form-control" id="api-client-linkSystem" data-cy="linkSystem" name="linkSystem" v-model="apiClient.linkSystem">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  apiClient.linkSystem && linkSystemOption.id === apiClient.linkSystem.id ? apiClient.linkSystem : linkSystemOption
                "
                v-for="linkSystemOption in linkSystems"
                :key="linkSystemOption.id"
              >
                {{ linkSystemOption.id }}
              </option>
            </select>
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
            :disabled="$v.apiClient.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./api-client-update.component.ts"></script>
