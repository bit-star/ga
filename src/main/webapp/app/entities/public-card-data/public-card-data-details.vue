<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="publicCardData">
        <h2 class="jh-entity-heading" data-cy="publicCardDataDetailsHeading">
          <span v-text="$t('gaApp.publicCardData.detail.title')">PublicCardData</span> {{ publicCardData.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('gaApp.publicCardData.title')">Title</span>
          </dt>
          <dd>
            <span>{{ publicCardData.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.publicCardData.requestid')">Requestid</span>
          </dt>
          <dd>
            <span>{{ publicCardData.requestid }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.publicCardData.workflowid')">Workflowid</span>
          </dt>
          <dd>
            <span>{{ publicCardData.workflowid }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.publicCardData.valid')">Valid</span>
          </dt>
          <dd>
            <span>{{ publicCardData.valid }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.publicCardData.finish')">Finish</span>
          </dt>
          <dd>
            <span>{{ publicCardData.finish }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.publicCardData.status')">Status</span>
          </dt>
          <dd>
            <span v-text="$t('gaApp.PublicDataCardStatus.' + publicCardData.status)">{{ publicCardData.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.publicCardData.variables')">Variables</span>
          </dt>
          <dd>
            <span>{{ publicCardData.variables }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.publicCardData.createdTime')">Created Time</span>
          </dt>
          <dd>
            <span v-if="publicCardData.createdTime">{{ $d(Date.parse(publicCardData.createdTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.publicCardData.link')">Link</span>
          </dt>
          <dd>
            <span>{{ publicCardData.link }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.publicCardData.updateLink')">Update Link</span>
          </dt>
          <dd>
            <span>{{ publicCardData.updateLink }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.publicCardData.agreeNum')">Agree Num</span>
          </dt>
          <dd>
            <span>{{ publicCardData.agreeNum }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.publicCardData.refuseNum')">Refuse Num</span>
          </dt>
          <dd>
            <span>{{ publicCardData.refuseNum }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.publicCardData.sysFullJsonObjJson')">Sys Full Json Obj Json</span>
          </dt>
          <dd>
            <span>{{ publicCardData.sysFullJsonObjJson }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.publicCardData.oaStatus')">Oa Status</span>
          </dt>
          <dd>
            <span v-text="$t('gaApp.WorkflowInstanceStatus.' + publicCardData.oaStatus)">{{ publicCardData.oaStatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.publicCardData.topboxes')">Topboxes</span>
          </dt>
          <dd>
            <div v-if="publicCardData.topboxes">
              <router-link :to="{ name: 'TopboxesView', params: { topboxesId: publicCardData.topboxes.id } }">{{
                publicCardData.topboxes.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('gaApp.publicCardData.workflowInstance')">Workflow Instance</span>
          </dt>
          <dd>
            <div v-if="publicCardData.workflowInstance">
              <router-link :to="{ name: 'WorkflowInstanceView', params: { workflowInstanceId: publicCardData.workflowInstance.id } }">{{
                publicCardData.workflowInstance.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('gaApp.publicCardData.conversation')">Conversation</span>
          </dt>
          <dd>
            <div v-if="publicCardData.conversation">
              <router-link :to="{ name: 'ConversationView', params: { conversationId: publicCardData.conversation.id } }">{{
                publicCardData.conversation.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="publicCardData.id"
          :to="{ name: 'PublicCardDataEdit', params: { publicCardDataId: publicCardData.id } }"
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

<script lang="ts" src="./public-card-data-details.component.ts"></script>
