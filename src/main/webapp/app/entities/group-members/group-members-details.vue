<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="groupMembers">
        <h2 class="jh-entity-heading" data-cy="groupMembersDetailsHeading">
          <span v-text="$t('gaApp.groupMembers.detail.title')">GroupMembers</span> {{ groupMembers.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('gaApp.groupMembers.groupRole')">Group Role</span>
          </dt>
          <dd>
            <span v-text="$t('gaApp.GroupRole.' + groupMembers.groupRole)">{{ groupMembers.groupRole }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.groupMembers.conversation')">Conversation</span>
          </dt>
          <dd>
            <div v-if="groupMembers.conversation">
              <router-link :to="{ name: 'ConversationView', params: { conversationId: groupMembers.conversation.id } }">{{
                groupMembers.conversation.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('gaApp.groupMembers.ddUser')">Dd User</span>
          </dt>
          <dd>
            <div v-if="groupMembers.ddUser">
              <router-link :to="{ name: 'DdUserView', params: { ddUserId: groupMembers.ddUser.id } }">{{
                groupMembers.ddUser.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="groupMembers.id"
          :to="{ name: 'GroupMembersEdit', params: { groupMembersId: groupMembers.id } }"
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

<script lang="ts" src="./group-members-details.component.ts"></script>
