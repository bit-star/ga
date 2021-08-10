<template>
  <div>
    <h2 id="page-heading" data-cy="ApproverHeading">
      <span v-text="$t('gaApp.approver.home.title')" id="approver-heading">Approvers</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('gaApp.approver.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ApproverCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-approver"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('gaApp.approver.home.createLabel')"> Create a new Approver </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && approvers && approvers.length === 0">
      <span v-text="$t('gaApp.approver.home.notFound')">No approvers found</span>
    </div>
    <div class="table-responsive" v-if="approvers && approvers.length > 0">
      <table class="table table-striped" aria-describedby="approvers">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('gaApp.approver.approverRole')">Approver Role</span></th>
            <th scope="row"><span v-text="$t('gaApp.approver.workflowInstance')">Workflow Instance</span></th>
            <th scope="row"><span v-text="$t('gaApp.approver.ddUser')">Dd User</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="approver in approvers" :key="approver.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ApproverView', params: { approverId: approver.id } }">{{ approver.id }}</router-link>
            </td>
            <td v-text="$t('gaApp.ApproverRole.' + approver.approverRole)">{{ approver.approverRole }}</td>
            <td>
              <div v-if="approver.workflowInstance">
                <router-link :to="{ name: 'WorkflowInstanceView', params: { workflowInstanceId: approver.workflowInstance.id } }">{{
                  approver.workflowInstance.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="approver.ddUser">
                <router-link :to="{ name: 'DdUserView', params: { ddUserId: approver.ddUser.id } }">{{ approver.ddUser.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ApproverView', params: { approverId: approver.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ApproverEdit', params: { approverId: approver.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(approver)"
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
        ><span id="gaApp.approver.delete.question" data-cy="approverDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-approver-heading" v-text="$t('gaApp.approver.delete.question', { id: removeId })">
          Are you sure you want to delete this Approver?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-approver"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeApprover()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./approver.component.ts"></script>
