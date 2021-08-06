<template>
  <div>
    <h2 id="page-heading" data-cy="OperationResultsHeading">
      <span v-text="$t('gaApp.operationResults.home.title')" id="operation-results-heading">Operation Results</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('gaApp.operationResults.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'OperationResultsCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-operation-results"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('gaApp.operationResults.home.createLabel')"> Create a new Operation Results </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && operationResults && operationResults.length === 0">
      <span v-text="$t('gaApp.operationResults.home.notFound')">No operationResults found</span>
    </div>
    <div class="table-responsive" v-if="operationResults && operationResults.length > 0">
      <table class="table table-striped" aria-describedby="operationResults">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('gaApp.operationResults.operationType')">Operation Type</span></th>
            <th scope="row"><span v-text="$t('gaApp.operationResults.time')">Time</span></th>
            <th scope="row"><span v-text="$t('gaApp.operationResults.text')">Text</span></th>
            <th scope="row"><span v-text="$t('gaApp.operationResults.ddUser')">Dd User</span></th>
            <th scope="row"><span v-text="$t('gaApp.operationResults.publicCardData')">Public Card Data</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="operationResults in operationResults" :key="operationResults.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'OperationResultsView', params: { operationResultsId: operationResults.id } }">{{
                operationResults.id
              }}</router-link>
            </td>
            <td v-text="$t('gaApp.OperationType.' + operationResults.operationType)">{{ operationResults.operationType }}</td>
            <td>{{ operationResults.time ? $d(Date.parse(operationResults.time), 'short') : '' }}</td>
            <td>{{ operationResults.text }}</td>
            <td>
              <div v-if="operationResults.ddUser">
                <router-link :to="{ name: 'DdUserView', params: { ddUserId: operationResults.ddUser.id } }">{{
                  operationResults.ddUser.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="operationResults.publicCardData">
                <router-link :to="{ name: 'PublicCardDataView', params: { publicCardDataId: operationResults.publicCardData.id } }">{{
                  operationResults.publicCardData.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'OperationResultsView', params: { operationResultsId: operationResults.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'OperationResultsEdit', params: { operationResultsId: operationResults.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(operationResults)"
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
        ><span id="gaApp.operationResults.delete.question" data-cy="operationResultsDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-operationResults-heading" v-text="$t('gaApp.operationResults.delete.question', { id: removeId })">
          Are you sure you want to delete this Operation Results?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-operationResults"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeOperationResults()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./operation-results.component.ts"></script>
