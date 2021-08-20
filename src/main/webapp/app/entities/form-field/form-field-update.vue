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
            <label class="form-control-label" v-text="$t('gaApp.formField.fieldName')" for="form-field-fieldName">Field Name</label>
            <input
              type="text"
              class="form-control"
              name="fieldName"
              id="form-field-fieldName"
              data-cy="fieldName"
              :class="{ valid: !$v.formField.fieldName.$invalid, invalid: $v.formField.fieldName.$invalid }"
              v-model="$v.formField.fieldName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.formField.oaId')" for="form-field-oaId">Oa Id</label>
            <input
              type="text"
              class="form-control"
              name="oaId"
              id="form-field-oaId"
              data-cy="oaId"
              :class="{ valid: !$v.formField.oaId.$invalid, invalid: $v.formField.oaId.$invalid }"
              v-model="$v.formField.oaId.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.formField.fielddbtype')" for="form-field-fielddbtype">Fielddbtype</label>
            <input
              type="text"
              class="form-control"
              name="fielddbtype"
              id="form-field-fielddbtype"
              data-cy="fielddbtype"
              :class="{ valid: !$v.formField.fielddbtype.$invalid, invalid: $v.formField.fielddbtype.$invalid }"
              v-model="$v.formField.fielddbtype.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.formField.labelName')" for="form-field-labelName">Label Name</label>
            <input
              type="text"
              class="form-control"
              name="labelName"
              id="form-field-labelName"
              data-cy="labelName"
              :class="{ valid: !$v.formField.labelName.$invalid, invalid: $v.formField.labelName.$invalid }"
              v-model="$v.formField.labelName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.formField.detailtable')" for="form-field-detailtable">Detailtable</label>
            <input
              type="text"
              class="form-control"
              name="detailtable"
              id="form-field-detailtable"
              data-cy="detailtable"
              :class="{ valid: !$v.formField.detailtable.$invalid, invalid: $v.formField.detailtable.$invalid }"
              v-model="$v.formField.detailtable.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.formField.show')" for="form-field-show">Show</label>
            <input
              type="checkbox"
              class="form-check"
              name="show"
              id="form-field-show"
              data-cy="show"
              :class="{ valid: !$v.formField.show.$invalid, invalid: $v.formField.show.$invalid }"
              v-model="$v.formField.show.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.formField.isCardField')" for="form-field-isCardField">Is Card Field</label>
            <input
              type="checkbox"
              class="form-check"
              name="isCardField"
              id="form-field-isCardField"
              data-cy="isCardField"
              :class="{ valid: !$v.formField.isCardField.$invalid, invalid: $v.formField.isCardField.$invalid }"
              v-model="$v.formField.isCardField.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.formField.isOaField')" for="form-field-isOaField">Is Oa Field</label>
            <input
              type="checkbox"
              class="form-check"
              name="isOaField"
              id="form-field-isOaField"
              data-cy="isOaField"
              :class="{ valid: !$v.formField.isOaField.$invalid, invalid: $v.formField.isOaField.$invalid }"
              v-model="$v.formField.isOaField.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.formField.isPrivate')" for="form-field-isPrivate">Is Private</label>
            <input
              type="checkbox"
              class="form-check"
              name="isPrivate"
              id="form-field-isPrivate"
              data-cy="isPrivate"
              :class="{ valid: !$v.formField.isPrivate.$invalid, invalid: $v.formField.isPrivate.$invalid }"
              v-model="$v.formField.isPrivate.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.formField.orderNum')" for="form-field-orderNum">Order Num</label>
            <input
              type="number"
              class="form-control"
              name="orderNum"
              id="form-field-orderNum"
              data-cy="orderNum"
              :class="{ valid: !$v.formField.orderNum.$invalid, invalid: $v.formField.orderNum.$invalid }"
              v-model.number="$v.formField.orderNum.$model"
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
