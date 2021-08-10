<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gaApp.approver.home.createOrEditLabel"
          data-cy="ApproverCreateUpdateHeading"
          v-text="$t('gaApp.approver.home.createOrEditLabel')"
        >
          Create or edit a Approver
        </h2>
        <div>
          <div class="form-group" v-if="approver.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="approver.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.approver.approverRole')" for="approver-approverRole">Approver Role</label>
            <select
              class="form-control"
              name="approverRole"
              :class="{ valid: !$v.approver.approverRole.$invalid, invalid: $v.approver.approverRole.$invalid }"
              v-model="$v.approver.approverRole.$model"
              id="approver-approverRole"
              data-cy="approverRole"
            >
              <option value="Approver" v-bind:label="$t('gaApp.ApproverRole.Approver')">Approver</option>
              <option value="Proposer" v-bind:label="$t('gaApp.ApproverRole.Proposer')">Proposer</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.approver.workflowInstance')" for="approver-workflowInstance"
              >Workflow Instance</label
            >
            <select
              class="form-control"
              id="approver-workflowInstance"
              data-cy="workflowInstance"
              name="workflowInstance"
              v-model="approver.workflowInstance"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  approver.workflowInstance && workflowInstanceOption.id === approver.workflowInstance.id
                    ? approver.workflowInstance
                    : workflowInstanceOption
                "
                v-for="workflowInstanceOption in workflowInstances"
                :key="workflowInstanceOption.id"
              >
                {{ workflowInstanceOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.approver.ddUser')" for="approver-ddUser">Dd User</label>
            <select class="form-control" id="approver-ddUser" data-cy="ddUser" name="ddUser" v-model="approver.ddUser">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="approver.ddUser && ddUserOption.id === approver.ddUser.id ? approver.ddUser : ddUserOption"
                v-for="ddUserOption in ddUsers"
                :key="ddUserOption.id"
              >
                {{ ddUserOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.approver.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./approver-update.component.ts"></script>
