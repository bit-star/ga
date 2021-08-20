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
            <label class="form-control-label" v-text="$t('gaApp.formField.fieldname')" for="form-field-fieldname">Fieldname</label>
            <input
              type="text"
              class="form-control"
              name="fieldname"
              id="form-field-fieldname"
              data-cy="fieldname"
              :class="{ valid: !$v.formField.fieldname.$invalid, invalid: $v.formField.fieldname.$invalid }"
              v-model="$v.formField.fieldname.$model"
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
            <label class="form-control-label" v-text="$t('gaApp.formField.labelname')" for="form-field-labelname">Labelname</label>
            <input
              type="text"
              class="form-control"
              name="labelname"
              id="form-field-labelname"
              data-cy="labelname"
              :class="{ valid: !$v.formField.labelname.$invalid, invalid: $v.formField.labelname.$invalid }"
              v-model="$v.formField.labelname.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.formField.fieldlabel')" for="form-field-fieldlabel">Fieldlabel</label>
            <input
              type="text"
              class="form-control"
              name="fieldlabel"
              id="form-field-fieldlabel"
              data-cy="fieldlabel"
              :class="{ valid: !$v.formField.fieldlabel.$invalid, invalid: $v.formField.fieldlabel.$invalid }"
              v-model="$v.formField.fieldlabel.$model"
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
