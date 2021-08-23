<template>
  <div>
    <h2 id="page-heading" data-cy="ApiInvokeLogHeading">
      <span v-text="$t('gaApp.apiInvokeLog.home.title')" id="api-invoke-log-heading">Api Invoke Logs</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('gaApp.apiInvokeLog.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ApiInvokeLogCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-api-invoke-log"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('gaApp.apiInvokeLog.home.createLabel')"> Create a new Api Invoke Log </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && apiInvokeLogs && apiInvokeLogs.length === 0">
      <span v-text="$t('gaApp.apiInvokeLog.home.notFound')">No apiInvokeLogs found</span>
    </div>
    <div class="table-responsive" v-if="apiInvokeLogs && apiInvokeLogs.length > 0">
      <table class="table table-striped" aria-describedby="apiInvokeLogs">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('gaApp.apiInvokeLog.login')">Login</span></th>
            <th scope="row"><span v-text="$t('gaApp.apiInvokeLog.apiName')">Api Name</span></th>
            <th scope="row"><span v-text="$t('gaApp.apiInvokeLog.method')">Method</span></th>
            <th scope="row"><span v-text="$t('gaApp.apiInvokeLog.direction')">Direction</span></th>
            <th scope="row"><span v-text="$t('gaApp.apiInvokeLog.httpStatusCode')">Http Status Code</span></th>
            <th scope="row"><span v-text="$t('gaApp.apiInvokeLog.time')">Time</span></th>
            <th scope="row"><span v-text="$t('gaApp.apiInvokeLog.reqeustData')">Reqeust Data</span></th>
            <th scope="row"><span v-text="$t('gaApp.apiInvokeLog.responseData')">Response Data</span></th>
            <th scope="row"><span v-text="$t('gaApp.apiInvokeLog.ok')">Ok</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="apiInvokeLog in apiInvokeLogs" :key="apiInvokeLog.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ApiInvokeLogView', params: { apiInvokeLogId: apiInvokeLog.id } }">{{
                apiInvokeLog.id
              }}</router-link>
            </td>
            <td>{{ apiInvokeLog.login }}</td>
            <td>{{ apiInvokeLog.apiName }}</td>
            <td v-text="$t('gaApp.HttpMethod.' + apiInvokeLog.method)">{{ apiInvokeLog.method }}</td>
            <td v-text="$t('gaApp.ApiDirection.' + apiInvokeLog.direction)">{{ apiInvokeLog.direction }}</td>
            <td>{{ apiInvokeLog.httpStatusCode }}</td>
            <td>{{ apiInvokeLog.time ? $d(Date.parse(apiInvokeLog.time), 'short') : '' }}</td>
            <td>{{ apiInvokeLog.reqeustData }}</td>
            <td>{{ apiInvokeLog.responseData }}</td>
            <td>{{ apiInvokeLog.ok }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ApiInvokeLogView', params: { apiInvokeLogId: apiInvokeLog.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ApiInvokeLogEdit', params: { apiInvokeLogId: apiInvokeLog.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(apiInvokeLog)"
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
        ><span id="gaApp.apiInvokeLog.delete.question" data-cy="apiInvokeLogDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-apiInvokeLog-heading" v-text="$t('gaApp.apiInvokeLog.delete.question', { id: removeId })">
          Are you sure you want to delete this Api Invoke Log?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-apiInvokeLog"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeApiInvokeLog()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./api-invoke-log.component.ts"></script>
