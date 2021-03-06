<template>
  <div>
    <h2 id="page-heading" data-cy="ConversationHeading">
      <span v-text="$t('gaApp.conversation.home.title')" id="conversation-heading">Conversations</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('gaApp.conversation.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ConversationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-conversation"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('gaApp.conversation.home.createLabel')"> Create a new Conversation </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && conversations && conversations.length === 0">
      <span v-text="$t('gaApp.conversation.home.notFound')">No conversations found</span>
    </div>
    <div class="table-responsive" v-if="conversations && conversations.length > 0">
      <table class="table table-striped" aria-describedby="conversations">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.name')">Name</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.title')">Title</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.owner')">Owner</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.ownerUserId')">Owner User Id</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.chatid')">Chatid</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.openConversationId')">Open Conversation Id</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.conversationTag')">Conversation Tag</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.useridlist')">Useridlist</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.uuid')">Uuid</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.icon')">Icon</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.showHistoryType')">Show History Type</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.searchable')">Searchable</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.validationType')">Validation Type</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.chatBannedType')">Chat Banned Type</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.mentionAllAuthority')">Mention All Authority</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.managementType')">Management Type</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.templateId')">Template Id</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.officialGroup')">Official Group</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.enableScenegroup')">Enable Scenegroup</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.groupUrl')">Group Url</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.time')">Time</span></th>
            <th scope="row"><span v-text="$t('gaApp.conversation.ddUser')">Dd User</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="conversation in conversations" :key="conversation.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ConversationView', params: { conversationId: conversation.id } }">{{
                conversation.id
              }}</router-link>
            </td>
            <td>{{ conversation.name }}</td>
            <td>{{ conversation.title }}</td>
            <td>{{ conversation.owner }}</td>
            <td>{{ conversation.ownerUserId }}</td>
            <td>{{ conversation.chatid }}</td>
            <td>{{ conversation.openConversationId }}</td>
            <td>{{ conversation.conversationTag }}</td>
            <td>{{ conversation.useridlist }}</td>
            <td>{{ conversation.uuid }}</td>
            <td>{{ conversation.icon }}</td>
            <td>{{ conversation.showHistoryType }}</td>
            <td>{{ conversation.searchable }}</td>
            <td>{{ conversation.validationType }}</td>
            <td>{{ conversation.chatBannedType }}</td>
            <td>{{ conversation.mentionAllAuthority }}</td>
            <td>{{ conversation.managementType }}</td>
            <td>{{ conversation.templateId }}</td>
            <td>{{ conversation.officialGroup }}</td>
            <td>{{ conversation.enableScenegroup }}</td>
            <td>{{ conversation.groupUrl }}</td>
            <td>{{ conversation.time ? $d(Date.parse(conversation.time), 'short') : '' }}</td>
            <td>
              <span v-for="(ddUser, i) in conversation.ddUsers" :key="ddUser.id"
                >{{ i > 0 ? ', ' : '' }}
                <router-link class="form-control-static" :to="{ name: 'DdUserView', params: { ddUserId: ddUser.id } }">{{
                  ddUser.id
                }}</router-link>
              </span>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ConversationView', params: { conversationId: conversation.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ConversationEdit', params: { conversationId: conversation.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(conversation)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="gaApp.conversation.delete.question" data-cy="conversationDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-conversation-heading" v-text="$t('gaApp.conversation.delete.question', { id: removeId })">
          Are you sure you want to delete this Conversation?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-conversation"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeConversation()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./conversation.component.ts"></script>
