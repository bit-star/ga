<template>
  <div>
    <h2 id="page-heading" data-cy="DdUserHeading">
      <span v-text="$t('gaApp.ddUser.home.title')" id="dd-user-heading">Dd Users</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('gaApp.ddUser.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'DdUserCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-dd-user"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('gaApp.ddUser.home.createLabel')"> Create a new Dd User </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && ddUsers && ddUsers.length === 0">
      <span v-text="$t('gaApp.ddUser.home.notFound')">No ddUsers found</span>
    </div>
    <div class="table-responsive" v-if="ddUsers && ddUsers.length > 0">
      <table class="table table-striped" aria-describedby="ddUsers">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.unionid')">Unionid</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.remark')">Remark</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.userid')">Userid</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.isLeaderInDepts')">Is Leader In Depts</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.isBoss')">Is Boss</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.hiredDate')">Hired Date</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.isSenior')">Is Senior</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.tel')">Tel</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.department')">Department</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.workPlace')">Work Place</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.orderInDepts')">Order In Depts</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.mobile')">Mobile</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.errmsg')">Errmsg</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.active')">Active</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.avatar')">Avatar</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.isAdmin')">Is Admin</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.isHide')">Is Hide</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.jobnumber')">Jobnumber</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.name')">Name</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.extattr')">Extattr</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.stateCode')">State Code</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.position')">Position</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.roles')">Roles</span></th>
            <th scope="row"><span v-text="$t('gaApp.ddUser.workflowInstance')">Workflow Instance</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="ddUser in ddUsers" :key="ddUser.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DdUserView', params: { ddUserId: ddUser.id } }">{{ ddUser.id }}</router-link>
            </td>
            <td>{{ ddUser.unionid }}</td>
            <td>{{ ddUser.remark }}</td>
            <td>{{ ddUser.userid }}</td>
            <td>{{ ddUser.isLeaderInDepts }}</td>
            <td>{{ ddUser.isBoss }}</td>
            <td>{{ ddUser.hiredDate }}</td>
            <td>{{ ddUser.isSenior }}</td>
            <td>{{ ddUser.tel }}</td>
            <td>{{ ddUser.department }}</td>
            <td>{{ ddUser.workPlace }}</td>
            <td>{{ ddUser.orderInDepts }}</td>
            <td>{{ ddUser.mobile }}</td>
            <td>{{ ddUser.errmsg }}</td>
            <td>{{ ddUser.active }}</td>
            <td>{{ ddUser.avatar }}</td>
            <td>{{ ddUser.isAdmin }}</td>
            <td>{{ ddUser.isHide }}</td>
            <td>{{ ddUser.jobnumber }}</td>
            <td>{{ ddUser.name }}</td>
            <td>{{ ddUser.extattr }}</td>
            <td>{{ ddUser.stateCode }}</td>
            <td>{{ ddUser.position }}</td>
            <td>{{ ddUser.roles }}</td>
            <td>
              <div v-if="ddUser.workflowInstance">
                <router-link :to="{ name: 'WorkflowInstanceView', params: { workflowInstanceId: ddUser.workflowInstance.id } }">{{
                  ddUser.workflowInstance.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DdUserView', params: { ddUserId: ddUser.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DdUserEdit', params: { ddUserId: ddUser.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(ddUser)"
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
        ><span id="gaApp.ddUser.delete.question" data-cy="ddUserDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-ddUser-heading" v-text="$t('gaApp.ddUser.delete.question', { id: removeId })">
          Are you sure you want to delete this Dd User?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-ddUser"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeDdUser()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./dd-user.component.ts"></script>
