<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="conversation">
        <h2 class="jh-entity-heading" data-cy="conversationDetailsHeading">
          <span v-text="$t('gaApp.conversation.detail.title')">Conversation</span> {{ conversation.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('gaApp.conversation.name')">Name</span>
          </dt>
          <dd>
            <span>{{ conversation.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.title')">Title</span>
          </dt>
          <dd>
            <span>{{ conversation.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.owner')">Owner</span>
          </dt>
          <dd>
            <span>{{ conversation.owner }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.ownerUserId')">Owner User Id</span>
          </dt>
          <dd>
            <span>{{ conversation.ownerUserId }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.chatid')">Chatid</span>
          </dt>
          <dd>
            <span>{{ conversation.chatid }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.openConversationId')">Open Conversation Id</span>
          </dt>
          <dd>
            <span>{{ conversation.openConversationId }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.conversationTag')">Conversation Tag</span>
          </dt>
          <dd>
            <span>{{ conversation.conversationTag }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.useridlist')">Useridlist</span>
          </dt>
          <dd>
            <span>{{ conversation.useridlist }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.uuid')">Uuid</span>
          </dt>
          <dd>
            <span>{{ conversation.uuid }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.icon')">Icon</span>
          </dt>
          <dd>
            <span>{{ conversation.icon }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.showHistoryType')">Show History Type</span>
          </dt>
          <dd>
            <span>{{ conversation.showHistoryType }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.searchable')">Searchable</span>
          </dt>
          <dd>
            <span>{{ conversation.searchable }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.validationType')">Validation Type</span>
          </dt>
          <dd>
            <span>{{ conversation.validationType }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.chatBannedType')">Chat Banned Type</span>
          </dt>
          <dd>
            <span>{{ conversation.chatBannedType }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.mentionAllAuthority')">Mention All Authority</span>
          </dt>
          <dd>
            <span>{{ conversation.mentionAllAuthority }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.managementType')">Management Type</span>
          </dt>
          <dd>
            <span>{{ conversation.managementType }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.templateId')">Template Id</span>
          </dt>
          <dd>
            <span>{{ conversation.templateId }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.officialGroup')">Official Group</span>
          </dt>
          <dd>
            <span>{{ conversation.officialGroup }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.enableScenegroup')">Enable Scenegroup</span>
          </dt>
          <dd>
            <span>{{ conversation.enableScenegroup }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.groupUrl')">Group Url</span>
          </dt>
          <dd>
            <span>{{ conversation.groupUrl }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.time')">Time</span>
          </dt>
          <dd>
            <span v-if="conversation.time">{{ $d(Date.parse(conversation.time), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.conversation.ddUser')">Dd User</span>
          </dt>
          <dd>
            <span v-for="(ddUser, i) in conversation.ddUsers" :key="ddUser.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'DdUserView', params: { ddUserId: ddUser.id } }">{{ ddUser.id }}</router-link>
            </span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="conversation.id"
          :to="{ name: 'ConversationEdit', params: { conversationId: conversation.id } }"
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

<script lang="ts" src="./conversation-details.component.ts"></script>
