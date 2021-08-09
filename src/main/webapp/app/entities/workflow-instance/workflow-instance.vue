<template>
  <div>
    <h2 id="page-heading" data-cy="WorkflowInstanceHeading">
      <span v-text="$t('gaApp.workflowInstance.home.title')" id="workflow-instance-heading">Workflow Instances</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('gaApp.workflowInstance.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'WorkflowInstanceCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-workflow-instance"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('gaApp.workflowInstance.home.createLabel')"> Create a new Workflow Instance </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && workflowInstances && workflowInstances.length === 0">
      <span v-text="$t('gaApp.workflowInstance.home.notFound')">No workflowInstances found</span>
    </div>
    <div class="table-responsive" v-if="workflowInstances && workflowInstances.length > 0">
      <table class="table table-striped" aria-describedby="workflowInstances">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowInstance.form')">Form</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowInstance.ddCardId')">Dd Card Id</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowInstance.title')">Title</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowInstance.ddCardTemplateId')">Dd Card Template Id</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowInstance.ddCardCallBackKey')">Dd Card Call Back Key</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowInstance.workflowTemplate')">Workflow Template</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowInstance.creator')">Creator</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="workflowInstance in workflowInstances" :key="workflowInstance.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'WorkflowInstanceView', params: { workflowInstanceId: workflowInstance.id } }">{{
                workflowInstance.id
              }}</router-link>
            </td>
            <td>{{ workflowInstance.form }}</td>
            <td>{{ workflowInstance.ddCardId }}</td>
            <td>{{ workflowInstance.title }}</td>
            <td>{{ workflowInstance.ddCardTemplateId }}</td>
            <td>{{ workflowInstance.ddCardCallBackKey }}</td>
            <td>
              <div v-if="workflowInstance.workflowTemplate">
                <router-link :to="{ name: 'WorkflowTemplateView', params: { workflowTemplateId: workflowInstance.workflowTemplate.id } }">{{
                  workflowInstance.workflowTemplate.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="workflowInstance.creator">
                <router-link :to="{ name: 'DdUserView', params: { ddUserId: workflowInstance.creator.id } }">{{
                  workflowInstance.creator.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'WorkflowInstanceView', params: { workflowInstanceId: workflowInstance.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'WorkflowInstanceEdit', params: { workflowInstanceId: workflowInstance.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(workflowInstance)"
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
        ><span id="gaApp.workflowInstance.delete.question" data-cy="workflowInstanceDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-workflowInstance-heading" v-text="$t('gaApp.workflowInstance.delete.question', { id: removeId })">
          Are you sure you want to delete this Workflow Instance?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-workflowInstance"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeWorkflowInstance()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./workflow-instance.component.ts"></script>
