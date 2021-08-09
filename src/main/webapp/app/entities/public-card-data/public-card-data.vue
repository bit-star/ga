<template>
  <div>
    <h2 id="page-heading" data-cy="PublicCardDataHeading">
      <span v-text="$t('gaApp.publicCardData.home.title')" id="public-card-data-heading">Public Card Data</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('gaApp.publicCardData.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PublicCardDataCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-public-card-data"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('gaApp.publicCardData.home.createLabel')"> Create a new Public Card Data </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && publicCardData && publicCardData.length === 0">
      <span v-text="$t('gaApp.publicCardData.home.notFound')">No publicCardData found</span>
    </div>
    <div class="table-responsive" v-if="publicCardData && publicCardData.length > 0">
      <table class="table table-striped" aria-describedby="publicCardData">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('gaApp.publicCardData.requestid')">Requestid</span></th>
            <th scope="row"><span v-text="$t('gaApp.publicCardData.workflowid')">Workflowid</span></th>
            <th scope="row"><span v-text="$t('gaApp.publicCardData.valid')">Valid</span></th>
            <th scope="row"><span v-text="$t('gaApp.publicCardData.name')">Name</span></th>
            <th scope="row"><span v-text="$t('gaApp.publicCardData.feeValue')">Fee Value</span></th>
            <th scope="row"><span v-text="$t('gaApp.publicCardData.reason')">Reason</span></th>
            <th scope="row"><span v-text="$t('gaApp.publicCardData.itemType')">Item Type</span></th>
            <th scope="row"><span v-text="$t('gaApp.publicCardData.typesOfFee')">Types Of Fee</span></th>
            <th scope="row"><span v-text="$t('gaApp.publicCardData.agree')">Agree</span></th>
            <th scope="row"><span v-text="$t('gaApp.publicCardData.finish')">Finish</span></th>
            <th scope="row"><span v-text="$t('gaApp.publicCardData.status')">Status</span></th>
            <th scope="row"><span v-text="$t('gaApp.publicCardData.content')">Content</span></th>
            <th scope="row"><span v-text="$t('gaApp.publicCardData.workflowInstance')">Workflow Instance</span></th>
            <th scope="row"><span v-text="$t('gaApp.publicCardData.conversation')">Conversation</span></th>
            <th scope="row"><span v-text="$t('gaApp.publicCardData.workflowTemplate')">Workflow Template</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="publicCardData in publicCardData" :key="publicCardData.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PublicCardDataView', params: { publicCardDataId: publicCardData.id } }">{{
                publicCardData.id
              }}</router-link>
            </td>
            <td>{{ publicCardData.requestid }}</td>
            <td>{{ publicCardData.workflowid }}</td>
            <td>{{ publicCardData.valid }}</td>
            <td>{{ publicCardData.name }}</td>
            <td>{{ publicCardData.feeValue }}</td>
            <td>{{ publicCardData.reason }}</td>
            <td>{{ publicCardData.itemType }}</td>
            <td>{{ publicCardData.typesOfFee }}</td>
            <td>{{ publicCardData.agree }}</td>
            <td>{{ publicCardData.finish }}</td>
            <td>{{ publicCardData.status }}</td>
            <td>{{ publicCardData.content }}</td>
            <td>
              <div v-if="publicCardData.workflowInstance">
                <router-link :to="{ name: 'WorkflowInstanceView', params: { workflowInstanceId: publicCardData.workflowInstance.id } }">{{
                  publicCardData.workflowInstance.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="publicCardData.conversation">
                <router-link :to="{ name: 'ConversationView', params: { conversationId: publicCardData.conversation.id } }">{{
                  publicCardData.conversation.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="publicCardData.workflowTemplate">
                <router-link :to="{ name: 'WorkflowTemplateView', params: { workflowTemplateId: publicCardData.workflowTemplate.id } }">{{
                  publicCardData.workflowTemplate.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'PublicCardDataView', params: { publicCardDataId: publicCardData.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'PublicCardDataEdit', params: { publicCardDataId: publicCardData.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(publicCardData)"
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
        ><span id="gaApp.publicCardData.delete.question" data-cy="publicCardDataDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-publicCardData-heading" v-text="$t('gaApp.publicCardData.delete.question', { id: removeId })">
          Are you sure you want to delete this Public Card Data?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-publicCardData"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePublicCardData()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./public-card-data.component.ts"></script>
