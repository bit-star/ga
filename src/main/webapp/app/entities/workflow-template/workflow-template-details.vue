<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="workflowTemplate">
        <h2 class="jh-entity-heading" data-cy="workflowTemplateDetailsHeading">
          <span v-text="$t('gaApp.workflowTemplate.detail.title')">WorkflowTemplate</span> {{ workflowTemplate.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('gaApp.workflowTemplate.formId')">Form Id</span>
          </dt>
          <dd>
            <span>{{ workflowTemplate.formId }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.workflowTemplate.workflowId')">Workflow Id</span>
          </dt>
          <dd>
            <span>{{ workflowTemplate.workflowId }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.workflowTemplate.workflowName')">Workflow Name</span>
          </dt>
          <dd>
            <span>{{ workflowTemplate.workflowName }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.workflowTemplate.workflowTypeId')">Workflow Type Id</span>
          </dt>
          <dd>
            <span>{{ workflowTemplate.workflowTypeId }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.workflowTemplate.workflowTypeName')">Workflow Type Name</span>
          </dt>
          <dd>
            <span>{{ workflowTemplate.workflowTypeName }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.workflowTemplate.ddGroupTemplateId')">Dd Group Template Id</span>
          </dt>
          <dd>
            <span>{{ workflowTemplate.ddGroupTemplateId }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.workflowTemplate.chatidField')">Chatid Field</span>
          </dt>
          <dd>
            <span>{{ workflowTemplate.chatidField }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.workflowTemplate.cardidField')">Cardid Field</span>
          </dt>
          <dd>
            <span>{{ workflowTemplate.cardidField }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.workflowTemplate.commentsField')">Comments Field</span>
          </dt>
          <dd>
            <span>{{ workflowTemplate.commentsField }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.workflowTemplate.linkSystem')">Link System</span>
          </dt>
          <dd>
            <div v-if="workflowTemplate.linkSystem">
              <router-link :to="{ name: 'LinkSystemView', params: { linkSystemId: workflowTemplate.linkSystem.id } }">{{
                workflowTemplate.linkSystem.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="workflowTemplate.id"
          :to="{ name: 'WorkflowTemplateEdit', params: { workflowTemplateId: workflowTemplate.id } }"
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

<script lang="ts" src="./workflow-template-details.component.ts"></script>
