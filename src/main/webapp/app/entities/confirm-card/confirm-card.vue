<template>
  <div>
    <h2 id="page-heading" data-cy="ConfirmCardHeading">
      <span v-text="$t('gaApp.confirmCard.home.title')" id="confirm-card-heading">Confirm Cards</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('gaApp.confirmCard.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ConfirmCardCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-confirm-card"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('gaApp.confirmCard.home.createLabel')"> Create a new Confirm Card </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && confirmCards && confirmCards.length === 0">
      <span v-text="$t('gaApp.confirmCard.home.notFound')">No confirmCards found</span>
    </div>
    <div class="table-responsive" v-if="confirmCards && confirmCards.length > 0">
      <table class="table table-striped" aria-describedby="confirmCards">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('gaApp.confirmCard.text')">Text</span></th>
            <th scope="row"><span v-text="$t('gaApp.confirmCard.finish')">Finish</span></th>
            <th scope="row"><span v-text="$t('gaApp.confirmCard.userId')">User Id</span></th>
            <th scope="row"><span v-text="$t('gaApp.confirmCard.link')">Link</span></th>
            <th scope="row"><span v-text="$t('gaApp.confirmCard.md1')">Md 1</span></th>
            <th scope="row"><span v-text="$t('gaApp.confirmCard.publicCardData')">Public Card Data</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="confirmCard in confirmCards" :key="confirmCard.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ConfirmCardView', params: { confirmCardId: confirmCard.id } }">{{ confirmCard.id }}</router-link>
            </td>
            <td>{{ confirmCard.text }}</td>
            <td>{{ confirmCard.finish }}</td>
            <td>{{ confirmCard.userId }}</td>
            <td>{{ confirmCard.link }}</td>
            <td>{{ confirmCard.md1 }}</td>
            <td>
              <div v-if="confirmCard.publicCardData">
                <router-link :to="{ name: 'PublicCardDataView', params: { publicCardDataId: confirmCard.publicCardData.id } }">{{
                  confirmCard.publicCardData.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ConfirmCardView', params: { confirmCardId: confirmCard.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ConfirmCardEdit', params: { confirmCardId: confirmCard.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(confirmCard)"
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
        ><span id="gaApp.confirmCard.delete.question" data-cy="confirmCardDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-confirmCard-heading" v-text="$t('gaApp.confirmCard.delete.question', { id: removeId })">
          Are you sure you want to delete this Confirm Card?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-confirmCard"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeConfirmCard()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./confirm-card.component.ts"></script>
