<template>
  <div>
    <h2 id="page-heading" data-cy="GroupMembersHeading">
      <span v-text="$t('gaApp.groupMembers.home.title')" id="group-members-heading">Group Members</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('gaApp.groupMembers.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'GroupMembersCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-group-members"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('gaApp.groupMembers.home.createLabel')"> Create a new Group Members </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && groupMembers && groupMembers.length === 0">
      <span v-text="$t('gaApp.groupMembers.home.notFound')">No groupMembers found</span>
    </div>
    <div class="table-responsive" v-if="groupMembers && groupMembers.length > 0">
      <table class="table table-striped" aria-describedby="groupMembers">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('gaApp.groupMembers.groupRole')">Group Role</span></th>
            <th scope="row"><span v-text="$t('gaApp.groupMembers.conversation')">Conversation</span></th>
            <th scope="row"><span v-text="$t('gaApp.groupMembers.ddUser')">Dd User</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="groupMembers in groupMembers" :key="groupMembers.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'GroupMembersView', params: { groupMembersId: groupMembers.id } }">{{
                groupMembers.id
              }}</router-link>
            </td>
            <td v-text="$t('gaApp.GroupRole.' + groupMembers.groupRole)">{{ groupMembers.groupRole }}</td>
            <td>
              <div v-if="groupMembers.conversation">
                <router-link :to="{ name: 'ConversationView', params: { conversationId: groupMembers.conversation.id } }">{{
                  groupMembers.conversation.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="groupMembers.ddUser">
                <router-link :to="{ name: 'DdUserView', params: { ddUserId: groupMembers.ddUser.id } }">{{
                  groupMembers.ddUser.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'GroupMembersView', params: { groupMembersId: groupMembers.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'GroupMembersEdit', params: { groupMembersId: groupMembers.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(groupMembers)"
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
        ><span id="gaApp.groupMembers.delete.question" data-cy="groupMembersDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-groupMembers-heading" v-text="$t('gaApp.groupMembers.delete.question', { id: removeId })">
          Are you sure you want to delete this Group Members?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-groupMembers"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeGroupMembers()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./group-members.component.ts"></script>
