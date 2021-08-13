<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gaApp.workflowInstance.home.createOrEditLabel"
          data-cy="WorkflowInstanceCreateUpdateHeading"
          v-text="$t('gaApp.workflowInstance.home.createOrEditLabel')"
        >
          Create or edit a WorkflowInstance
        </h2>
        <div>
          <div class="form-group" v-if="workflowInstance.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="workflowInstance.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.workflowInstance.form')" for="workflow-instance-form">Form</label>
            <textarea
              class="form-control"
              name="form"
              id="workflow-instance-form"
              data-cy="form"
              :class="{ valid: !$v.workflowInstance.form.$invalid, invalid: $v.workflowInstance.form.$invalid }"
              v-model="$v.workflowInstance.form.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.workflowInstance.ddCardId')" for="workflow-instance-ddCardId"
              >Dd Card Id</label
            >
            <input
              type="text"
              class="form-control"
              name="ddCardId"
              id="workflow-instance-ddCardId"
              data-cy="ddCardId"
              :class="{ valid: !$v.workflowInstance.ddCardId.$invalid, invalid: $v.workflowInstance.ddCardId.$invalid }"
              v-model="$v.workflowInstance.ddCardId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.workflowInstance.title')" for="workflow-instance-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="workflow-instance-title"
              data-cy="title"
              :class="{ valid: !$v.workflowInstance.title.$invalid, invalid: $v.workflowInstance.title.$invalid }"
              v-model="$v.workflowInstance.title.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('gaApp.workflowInstance.ddCardTemplateId')"
              for="workflow-instance-ddCardTemplateId"
              >Dd Card Template Id</label
            >
            <input
              type="text"
              class="form-control"
              name="ddCardTemplateId"
              id="workflow-instance-ddCardTemplateId"
              data-cy="ddCardTemplateId"
              :class="{ valid: !$v.workflowInstance.ddCardTemplateId.$invalid, invalid: $v.workflowInstance.ddCardTemplateId.$invalid }"
              v-model="$v.workflowInstance.ddCardTemplateId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('gaApp.workflowInstance.ddCardCallBackKey')"
              for="workflow-instance-ddCardCallBackKey"
              >Dd Card Call Back Key</label
            >
            <input
              type="text"
              class="form-control"
              name="ddCardCallBackKey"
              id="workflow-instance-ddCardCallBackKey"
              data-cy="ddCardCallBackKey"
              :class="{ valid: !$v.workflowInstance.ddCardCallBackKey.$invalid, invalid: $v.workflowInstance.ddCardCallBackKey.$invalid }"
              v-model="$v.workflowInstance.ddCardCallBackKey.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.workflowInstance.requestId')" for="workflow-instance-requestId"
              >Request Id</label
            >
            <input
              type="text"
              class="form-control"
              name="requestId"
              id="workflow-instance-requestId"
              data-cy="requestId"
              :class="{ valid: !$v.workflowInstance.requestId.$invalid, invalid: $v.workflowInstance.requestId.$invalid }"
              v-model="$v.workflowInstance.requestId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.workflowInstance.status')" for="workflow-instance-status">Status</label>
            <select
              class="form-control"
              name="status"
              :class="{ valid: !$v.workflowInstance.status.$invalid, invalid: $v.workflowInstance.status.$invalid }"
              v-model="$v.workflowInstance.status.$model"
              id="workflow-instance-status"
              data-cy="status"
            >
              <option value="Launch" v-bind:label="$t('gaApp.WorkflowInstanceStatus.Launch')">Launch</option>
              <option value="Refuse" v-bind:label="$t('gaApp.WorkflowInstanceStatus.Refuse')">Refuse</option>
              <option value="Agree" v-bind:label="$t('gaApp.WorkflowInstanceStatus.Agree')">Agree</option>
              <option value="Archive" v-bind:label="$t('gaApp.WorkflowInstanceStatus.Archive')">Archive</option>
            </select>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('gaApp.workflowInstance.workflowTemplate')"
              for="workflow-instance-workflowTemplate"
              >Workflow Template</label
            >
            <select
              class="form-control"
              id="workflow-instance-workflowTemplate"
              data-cy="workflowTemplate"
              name="workflowTemplate"
              v-model="workflowInstance.workflowTemplate"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  workflowInstance.workflowTemplate && workflowTemplateOption.id === workflowInstance.workflowTemplate.id
                    ? workflowInstance.workflowTemplate
                    : workflowTemplateOption
                "
                v-for="workflowTemplateOption in workflowTemplates"
                :key="workflowTemplateOption.id"
              >
                {{ workflowTemplateOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.workflowInstance.creator')" for="workflow-instance-creator">Creator</label>
            <select class="form-control" id="workflow-instance-creator" data-cy="creator" name="creator" v-model="workflowInstance.creator">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  workflowInstance.creator && ddUserOption.id === workflowInstance.creator.id ? workflowInstance.creator : ddUserOption
                "
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
            :disabled="$v.workflowInstance.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./workflow-instance-update.component.ts"></script>
