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
            <label class="form-control-label" v-text="$t('gaApp.workflowInstance.publicCardData')" for="workflow-instance-publicCardData"
              >Public Card Data</label
            >
            <select
              class="form-control"
              id="workflow-instance-publicCardData"
              data-cy="publicCardData"
              name="publicCardData"
              v-model="workflowInstance.publicCardData"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  workflowInstance.publicCardData && publicCardDataOption.id === workflowInstance.publicCardData.id
                    ? workflowInstance.publicCardData
                    : publicCardDataOption
                "
                v-for="publicCardDataOption in publicCardData"
                :key="publicCardDataOption.id"
              >
                {{ publicCardDataOption.id }}
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
