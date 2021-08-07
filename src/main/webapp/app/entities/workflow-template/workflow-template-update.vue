<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gaApp.workflowTemplate.home.createOrEditLabel"
          data-cy="WorkflowTemplateCreateUpdateHeading"
          v-text="$t('gaApp.workflowTemplate.home.createOrEditLabel')"
        >
          Create or edit a WorkflowTemplate
        </h2>
        <div>
          <div class="form-group" v-if="workflowTemplate.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="workflowTemplate.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.workflowTemplate.formId')" for="workflow-template-formId">Form Id</label>
            <input
              type="text"
              class="form-control"
              name="formId"
              id="workflow-template-formId"
              data-cy="formId"
              :class="{ valid: !$v.workflowTemplate.formId.$invalid, invalid: $v.workflowTemplate.formId.$invalid }"
              v-model="$v.workflowTemplate.formId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.workflowTemplate.workflowId')" for="workflow-template-workflowId"
              >Workflow Id</label
            >
            <input
              type="text"
              class="form-control"
              name="workflowId"
              id="workflow-template-workflowId"
              data-cy="workflowId"
              :class="{ valid: !$v.workflowTemplate.workflowId.$invalid, invalid: $v.workflowTemplate.workflowId.$invalid }"
              v-model="$v.workflowTemplate.workflowId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.workflowTemplate.workflowName')" for="workflow-template-workflowName"
              >Workflow Name</label
            >
            <input
              type="text"
              class="form-control"
              name="workflowName"
              id="workflow-template-workflowName"
              data-cy="workflowName"
              :class="{ valid: !$v.workflowTemplate.workflowName.$invalid, invalid: $v.workflowTemplate.workflowName.$invalid }"
              v-model="$v.workflowTemplate.workflowName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.workflowTemplate.workflowTypeId')" for="workflow-template-workflowTypeId"
              >Workflow Type Id</label
            >
            <input
              type="text"
              class="form-control"
              name="workflowTypeId"
              id="workflow-template-workflowTypeId"
              data-cy="workflowTypeId"
              :class="{ valid: !$v.workflowTemplate.workflowTypeId.$invalid, invalid: $v.workflowTemplate.workflowTypeId.$invalid }"
              v-model="$v.workflowTemplate.workflowTypeId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('gaApp.workflowTemplate.workflowTypeName')"
              for="workflow-template-workflowTypeName"
              >Workflow Type Name</label
            >
            <input
              type="text"
              class="form-control"
              name="workflowTypeName"
              id="workflow-template-workflowTypeName"
              data-cy="workflowTypeName"
              :class="{ valid: !$v.workflowTemplate.workflowTypeName.$invalid, invalid: $v.workflowTemplate.workflowTypeName.$invalid }"
              v-model="$v.workflowTemplate.workflowTypeName.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('gaApp.workflowTemplate.ddGroupTemplateId')"
              for="workflow-template-ddGroupTemplateId"
              >Dd Group Template Id</label
            >
            <input
              type="text"
              class="form-control"
              name="ddGroupTemplateId"
              id="workflow-template-ddGroupTemplateId"
              data-cy="ddGroupTemplateId"
              :class="{ valid: !$v.workflowTemplate.ddGroupTemplateId.$invalid, invalid: $v.workflowTemplate.ddGroupTemplateId.$invalid }"
              v-model="$v.workflowTemplate.ddGroupTemplateId.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="$t('gaApp.workflowTemplate.eMobileCreatePageUrl')"
              for="workflow-template-eMobileCreatePageUrl"
              >E Mobile Create Page Url</label
            >
            <input
              type="text"
              class="form-control"
              name="eMobileCreatePageUrl"
              id="workflow-template-eMobileCreatePageUrl"
              data-cy="eMobileCreatePageUrl"
              :class="{
                valid: !$v.workflowTemplate.eMobileCreatePageUrl.$invalid,
                invalid: $v.workflowTemplate.eMobileCreatePageUrl.$invalid,
              }"
              v-model="$v.workflowTemplate.eMobileCreatePageUrl.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.workflowTemplate.chatidField')" for="workflow-template-chatidField"
              >Chatid Field</label
            >
            <input
              type="text"
              class="form-control"
              name="chatidField"
              id="workflow-template-chatidField"
              data-cy="chatidField"
              :class="{ valid: !$v.workflowTemplate.chatidField.$invalid, invalid: $v.workflowTemplate.chatidField.$invalid }"
              v-model="$v.workflowTemplate.chatidField.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.workflowTemplate.cardidField')" for="workflow-template-cardidField"
              >Cardid Field</label
            >
            <input
              type="text"
              class="form-control"
              name="cardidField"
              id="workflow-template-cardidField"
              data-cy="cardidField"
              :class="{ valid: !$v.workflowTemplate.cardidField.$invalid, invalid: $v.workflowTemplate.cardidField.$invalid }"
              v-model="$v.workflowTemplate.cardidField.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.workflowTemplate.commentsField')" for="workflow-template-commentsField"
              >Comments Field</label
            >
            <input
              type="text"
              class="form-control"
              name="commentsField"
              id="workflow-template-commentsField"
              data-cy="commentsField"
              :class="{ valid: !$v.workflowTemplate.commentsField.$invalid, invalid: $v.workflowTemplate.commentsField.$invalid }"
              v-model="$v.workflowTemplate.commentsField.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.workflowTemplate.linkSystem')" for="workflow-template-linkSystem"
              >Link System</label
            >
            <select
              class="form-control"
              id="workflow-template-linkSystem"
              data-cy="linkSystem"
              name="linkSystem"
              v-model="workflowTemplate.linkSystem"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  workflowTemplate.linkSystem && linkSystemOption.id === workflowTemplate.linkSystem.id
                    ? workflowTemplate.linkSystem
                    : linkSystemOption
                "
                v-for="linkSystemOption in linkSystems"
                :key="linkSystemOption.id"
              >
                {{ linkSystemOption.id }}
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
            :disabled="$v.workflowTemplate.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./workflow-template-update.component.ts"></script>
