<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gaApp.formField.home.createOrEditLabel"
          data-cy="FormFieldCreateUpdateHeading"
          v-text="$t('gaApp.formField.home.createOrEditLabel')"
        >
          Create or edit a FormField
        </h2>
        <div>
          <div class="form-group" v-if="formField.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="formField.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.formField.name')" for="form-field-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="form-field-name"
              data-cy="name"
              :class="{ valid: !$v.formField.name.$invalid, invalid: $v.formField.name.$invalid }"
              v-model="$v.formField.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.formField.value')" for="form-field-value">Value</label>
            <input
              type="text"
              class="form-control"
              name="value"
              id="form-field-value"
              data-cy="value"
              :class="{ valid: !$v.formField.value.$invalid, invalid: $v.formField.value.$invalid }"
              v-model="$v.formField.value.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.formField.lable')" for="form-field-lable">Lable</label>
            <input
              type="text"
              class="form-control"
              name="lable"
              id="form-field-lable"
              data-cy="lable"
              :class="{ valid: !$v.formField.lable.$invalid, invalid: $v.formField.lable.$invalid }"
              v-model="$v.formField.lable.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.formField.workflowTemplate')" for="form-field-workflowTemplate"
              >Workflow Template</label
            >
            <select
              class="form-control"
              id="form-field-workflowTemplate"
              data-cy="workflowTemplate"
              name="workflowTemplate"
              v-model="formField.workflowTemplate"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  formField.workflowTemplate && workflowTemplateOption.id === formField.workflowTemplate.id
                    ? formField.workflowTemplate
                    : workflowTemplateOption
                "
                v-for="workflowTemplateOption in workflowTemplates"
                :key="workflowTemplateOption.id"
              >
                {{ workflowTemplateOption.id }}
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
            :disabled="$v.formField.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./form-field-update.component.ts"></script>
