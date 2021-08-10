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
