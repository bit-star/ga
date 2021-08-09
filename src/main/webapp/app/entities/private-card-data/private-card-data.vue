<template>
  <div>
    <h2 id="page-heading" data-cy="PrivateCardDataHeading">
      <span v-text="$t('gaApp.privateCardData.home.title')" id="private-card-data-heading">Private Card Data</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('gaApp.privateCardData.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PrivateCardDataCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-private-card-data"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('gaApp.privateCardData.home.createLabel')"> Create a new Private Card Data </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && privateCardData && privateCardData.length === 0">
      <span v-text="$t('gaApp.privateCardData.home.notFound')">No privateCardData found</span>
    </div>
    <div class="table-responsive" v-if="privateCardData && privateCardData.length > 0">
      <table class="table table-striped" aria-describedby="privateCardData">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('gaApp.privateCardData.agree')">Agree</span></th>
            <th scope="row"><span v-text="$t('gaApp.privateCardData.finish')">Finish</span></th>
            <th scope="row"><span v-text="$t('gaApp.privateCardData.authority')">Authority</span></th>
            <th scope="row"><span v-text="$t('gaApp.privateCardData.publicCardData')">Public Card Data</span></th>
            <th scope="row"><span v-text="$t('gaApp.privateCardData.ddUser')">Dd User</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="privateCardData in privateCardData" :key="privateCardData.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PrivateCardDataView', params: { privateCardDataId: privateCardData.id } }">{{
                privateCardData.id
              }}</router-link>
            </td>
            <td>{{ privateCardData.agree }}</td>
            <td>{{ privateCardData.finish }}</td>
            <td>{{ privateCardData.authority }}</td>
            <td>
              <div v-if="privateCardData.publicCardData">
                <router-link :to="{ name: 'PublicCardDataView', params: { publicCardDataId: privateCardData.publicCardData.id } }">{{
                  privateCardData.publicCardData.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="privateCardData.ddUser">
                <router-link :to="{ name: 'DdUserView', params: { ddUserId: privateCardData.ddUser.id } }">{{
                  privateCardData.ddUser.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'PrivateCardDataView', params: { privateCardDataId: privateCardData.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'PrivateCardDataEdit', params: { privateCardDataId: privateCardData.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(privateCardData)"
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
        ><span id="gaApp.privateCardData.delete.question" data-cy="privateCardDataDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-privateCardData-heading" v-text="$t('gaApp.privateCardData.delete.question', { id: removeId })">
          Are you sure you want to delete this Private Card Data?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-privateCardData"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePrivateCardData()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./private-card-data.component.ts"></script>
