<template>
  <div>
    <h2 id="page-heading" data-cy="WorkflowTemplateHeading">
      <span v-text="$t('gaApp.workflowTemplate.home.title')" id="workflow-template-heading">Workflow Templates</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('gaApp.workflowTemplate.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'WorkflowTemplateCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-workflow-template"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('gaApp.workflowTemplate.home.createLabel')"> Create a new Workflow Template </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && workflowTemplates && workflowTemplates.length === 0">
      <span v-text="$t('gaApp.workflowTemplate.home.notFound')">No workflowTemplates found</span>
    </div>
    <div class="table-responsive" v-if="workflowTemplates && workflowTemplates.length > 0">
      <table class="table table-striped" aria-describedby="workflowTemplates">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowTemplate.formId')">Form Id</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowTemplate.workflowId')">Workflow Id</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowTemplate.workflowName')">Workflow Name</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowTemplate.workflowTypeId')">Workflow Type Id</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowTemplate.workflowTypeName')">Workflow Type Name</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowTemplate.ddGroupTemplateId')">Dd Group Template Id</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowTemplate.ddCardTemplateId')">Dd Card Template Id</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowTemplate.ddCardCallBackKey')">Dd Card Call Back Key</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowTemplate.eMobileCreatePageUrl')">E Mobile Create Page Url</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowTemplate.chatidField')">Chatid Field</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowTemplate.sourceField')">Source Field</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowTemplate.commentsField')">Comments Field</span></th>
            <th scope="row"><span v-text="$t('gaApp.workflowTemplate.linkSystem')">Link System</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="workflowTemplate in workflowTemplates" :key="workflowTemplate.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'WorkflowTemplateView', params: { workflowTemplateId: workflowTemplate.id } }">{{
                workflowTemplate.id
              }}</router-link>
            </td>
            <td>{{ workflowTemplate.formId }}</td>
            <td>{{ workflowTemplate.workflowId }}</td>
            <td>{{ workflowTemplate.workflowName }}</td>
            <td>{{ workflowTemplate.workflowTypeId }}</td>
            <td>{{ workflowTemplate.workflowTypeName }}</td>
            <td>{{ workflowTemplate.ddGroupTemplateId }}</td>
            <td>{{ workflowTemplate.ddCardTemplateId }}</td>
            <td>{{ workflowTemplate.ddCardCallBackKey }}</td>
            <td>{{ workflowTemplate.eMobileCreatePageUrl }}</td>
            <td>{{ workflowTemplate.chatidField }}</td>
            <td>{{ workflowTemplate.sourceField }}</td>
            <td>{{ workflowTemplate.commentsField }}</td>
            <td>
              <div v-if="workflowTemplate.linkSystem">
                <router-link :to="{ name: 'LinkSystemView', params: { linkSystemId: workflowTemplate.linkSystem.id } }">{{
                  workflowTemplate.linkSystem.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'WorkflowTemplateView', params: { workflowTemplateId: workflowTemplate.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'WorkflowTemplateEdit', params: { workflowTemplateId: workflowTemplate.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(workflowTemplate)"
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
        ><span id="gaApp.workflowTemplate.delete.question" data-cy="workflowTemplateDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-workflowTemplate-heading" v-text="$t('gaApp.workflowTemplate.delete.question', { id: removeId })">
          Are you sure you want to delete this Workflow Template?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-workflowTemplate"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeWorkflowTemplate()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./workflow-template.component.ts"></script>
