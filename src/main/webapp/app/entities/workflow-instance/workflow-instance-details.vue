<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="workflowInstance">
        <h2 class="jh-entity-heading" data-cy="workflowInstanceDetailsHeading">
          <span v-text="$t('gaApp.workflowInstance.detail.title')">WorkflowInstance</span> {{ workflowInstance.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('gaApp.workflowInstance.form')">Form</span>
          </dt>
          <dd>
            <span>{{ workflowInstance.form }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.workflowInstance.ddCardId')">Dd Card Id</span>
          </dt>
          <dd>
            <span>{{ workflowInstance.ddCardId }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.workflowInstance.title')">Title</span>
          </dt>
          <dd>
            <span>{{ workflowInstance.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.workflowInstance.ddCardTemplateId')">Dd Card Template Id</span>
          </dt>
          <dd>
            <span>{{ workflowInstance.ddCardTemplateId }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.workflowInstance.ddCardCallBackKey')">Dd Card Call Back Key</span>
          </dt>
          <dd>
            <span>{{ workflowInstance.ddCardCallBackKey }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.workflowInstance.requestId')">Request Id</span>
          </dt>
          <dd>
            <span>{{ workflowInstance.requestId }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.workflowInstance.status')">Status</span>
          </dt>
          <dd>
            <span v-text="$t('gaApp.WorkflowInstanceStatus.' + workflowInstance.status)">{{ workflowInstance.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.workflowInstance.workflowTemplate')">Workflow Template</span>
          </dt>
          <dd>
            <div v-if="workflowInstance.workflowTemplate">
              <router-link :to="{ name: 'WorkflowTemplateView', params: { workflowTemplateId: workflowInstance.workflowTemplate.id } }">{{
                workflowInstance.workflowTemplate.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('gaApp.workflowInstance.creator')">Creator</span>
          </dt>
          <dd>
            <div v-if="workflowInstance.creator">
              <router-link :to="{ name: 'DdUserView', params: { ddUserId: workflowInstance.creator.id } }">{{
                workflowInstance.creator.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="workflowInstance.id"
          :to="{ name: 'WorkflowInstanceEdit', params: { workflowInstanceId: workflowInstance.id } }"
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

<script lang="ts" src="./workflow-instance-details.component.ts"></script>
