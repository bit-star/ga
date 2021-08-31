<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="approver">
        <h2 class="jh-entity-heading" data-cy="approverDetailsHeading">
          <span v-text="$t('gaApp.approver.detail.title')">Approver</span> {{ approver.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('gaApp.approver.approverRole')">Approver Role</span>
          </dt>
          <dd>
            <span v-text="$t('gaApp.ApproverRole.' + approver.approverRole)">{{ approver.approverRole }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.approver.oaUserId')">Oa User Id</span>
          </dt>
          <dd>
            <span>{{ approver.oaUserId }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.approver.workflowInstance')">Workflow Instance</span>
          </dt>
          <dd>
            <div v-if="approver.workflowInstance">
              <router-link :to="{ name: 'WorkflowInstanceView', params: { workflowInstanceId: approver.workflowInstance.id } }">{{
                approver.workflowInstance.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('gaApp.approver.ddUser')">Dd User</span>
          </dt>
          <dd>
            <div v-if="approver.ddUser">
              <router-link :to="{ name: 'DdUserView', params: { ddUserId: approver.ddUser.id } }">{{ approver.ddUser.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="approver.id" :to="{ name: 'ApproverEdit', params: { approverId: approver.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./approver-details.component.ts"></script>
