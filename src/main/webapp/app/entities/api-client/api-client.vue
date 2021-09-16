<template>
  <div>
    <h2 id="page-heading" data-cy="ApiClientHeading">
      <span v-text="$t('gaApp.apiClient.home.title')" id="api-client-heading">Api Clients</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('gaApp.apiClient.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ApiClientCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-api-client"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('gaApp.apiClient.home.createLabel')"> Create a new Api Client </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && apiClients && apiClients.length === 0">
      <span v-text="$t('gaApp.apiClient.home.notFound')">No apiClients found</span>
    </div>
    <div class="table-responsive" v-if="apiClients && apiClients.length > 0">
      <table class="table table-striped" aria-describedby="apiClients">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('gaApp.apiClient.name')">Name</span></th>
            <th scope="row"><span v-text="$t('gaApp.apiClient.apiKey')">Api Key</span></th>
            <th scope="row"><span v-text="$t('gaApp.apiClient.apiSecret')">Api Secret</span></th>
            <th scope="row"><span v-text="$t('gaApp.apiClient.enable')">Enable</span></th>
            <th scope="row"><span v-text="$t('gaApp.apiClient.linkSystem')">Link System</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="apiClient in apiClients" :key="apiClient.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ApiClientView', params: { apiClientId: apiClient.id } }">{{ apiClient.id }}</router-link>
            </td>
            <td>{{ apiClient.name }}</td>
            <td>{{ apiClient.apiKey }}</td>
            <td>{{ apiClient.apiSecret }}</td>
            <td>{{ apiClient.enable }}</td>
            <td>
              <div v-if="apiClient.linkSystem">
                <router-link :to="{ name: 'LinkSystemView', params: { linkSystemId: apiClient.linkSystem.id } }">{{
                  apiClient.linkSystem.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ApiClientView', params: { apiClientId: apiClient.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ApiClientEdit', params: { apiClientId: apiClient.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(apiClient)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="gaApp.apiClient.delete.question" data-cy="apiClientDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-apiClient-heading" v-text="$t('gaApp.apiClient.delete.question', { id: removeId })">
          Are you sure you want to delete this Api Client?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-apiClient"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeApiClient()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./api-client.component.ts"></script>
