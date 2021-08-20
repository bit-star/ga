<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="formField">
        <h2 class="jh-entity-heading" data-cy="formFieldDetailsHeading">
          <span v-text="$t('gaApp.formField.detail.title')">FormField</span> {{ formField.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('gaApp.formField.fieldname')">Fieldname</span>
          </dt>
          <dd>
            <span>{{ formField.fieldname }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.formField.value')">Value</span>
          </dt>
          <dd>
            <span>{{ formField.value }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.formField.fielddbtype')">Fielddbtype</span>
          </dt>
          <dd>
            <span>{{ formField.fielddbtype }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.formField.labelname')">Labelname</span>
          </dt>
          <dd>
            <span>{{ formField.labelname }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.formField.fieldlabel')">Fieldlabel</span>
          </dt>
          <dd>
            <span>{{ formField.fieldlabel }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.formField.detailtable')">Detailtable</span>
          </dt>
          <dd>
            <span>{{ formField.detailtable }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.formField.show')">Show</span>
          </dt>
          <dd>
            <span>{{ formField.show }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.formField.isCardField')">Is Card Field</span>
          </dt>
          <dd>
            <span>{{ formField.isCardField }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.formField.orderNum')">Order Num</span>
          </dt>
          <dd>
            <span>{{ formField.orderNum }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.formField.workflowTemplate')">Workflow Template</span>
          </dt>
          <dd>
            <div v-if="formField.workflowTemplate">
              <router-link :to="{ name: 'WorkflowTemplateView', params: { workflowTemplateId: formField.workflowTemplate.id } }">{{
                formField.workflowTemplate.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="formField.id"
          :to="{ name: 'FormFieldEdit', params: { formFieldId: formField.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./form-field-details.component.ts"></script>
