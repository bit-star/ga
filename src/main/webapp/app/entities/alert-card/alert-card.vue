<template>
  <div>
    <h2 id="page-heading" data-cy="AlertCardHeading">
      <span v-text="$t('gaApp.alertCard.home.title')" id="alert-card-heading">Alert Cards</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('gaApp.alertCard.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AlertCardCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-alert-card"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('gaApp.alertCard.home.createLabel')"> Create a new Alert Card </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && alertCards && alertCards.length === 0">
      <span v-text="$t('gaApp.alertCard.home.notFound')">No alertCards found</span>
    </div>
    <div class="table-responsive" v-if="alertCards && alertCards.length > 0">
      <table class="table table-striped" aria-describedby="alertCards">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('gaApp.alertCard.text')">Text</span></th>
            <th scope="row"><span v-text="$t('gaApp.alertCard.userId')">User Id</span></th>
            <th scope="row"><span v-text="$t('gaApp.alertCard.publicCardData')">Public Card Data</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="alertCard in alertCards" :key="alertCard.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AlertCardView', params: { alertCardId: alertCard.id } }">{{ alertCard.id }}</router-link>
            </td>
            <td>{{ alertCard.text }}</td>
            <td>{{ alertCard.userId }}</td>
            <td>
              <div v-if="alertCard.publicCardData">
                <router-link :to="{ name: 'PublicCardDataView', params: { publicCardDataId: alertCard.publicCardData.id } }">{{
                  alertCard.publicCardData.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AlertCardView', params: { alertCardId: alertCard.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AlertCardEdit', params: { alertCardId: alertCard.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(alertCard)"
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
        ><span id="gaApp.alertCard.delete.question" data-cy="alertCardDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-alertCard-heading" v-text="$t('gaApp.alertCard.delete.question', { id: removeId })">
          Are you sure you want to delete this Alert Card?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-alertCard"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAlertCard()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./alert-card.component.ts"></script>
