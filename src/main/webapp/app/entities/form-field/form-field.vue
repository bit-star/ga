<template>
  <div>
    <h2 id="page-heading" data-cy="FormFieldHeading">
      <span v-text="$t('gaApp.formField.home.title')" id="form-field-heading">Form Fields</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('gaApp.formField.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'FormFieldCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-form-field"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('gaApp.formField.home.createLabel')"> Create a new Form Field </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && formFields && formFields.length === 0">
      <span v-text="$t('gaApp.formField.home.notFound')">No formFields found</span>
    </div>
    <div class="table-responsive" v-if="formFields && formFields.length > 0">
      <table class="table table-striped" aria-describedby="formFields">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fieldName')">
              <span v-text="$t('gaApp.formField.fieldName')">Field Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fieldName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('oaId')">
              <span v-text="$t('gaApp.formField.oaId')">Oa Id</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'oaId'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fielddbtype')">
              <span v-text="$t('gaApp.formField.fielddbtype')">Fielddbtype</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fielddbtype'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('labelName')">
              <span v-text="$t('gaApp.formField.labelName')">Label Name</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'labelName'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('detailtable')">
              <span v-text="$t('gaApp.formField.detailtable')">Detailtable</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'detailtable'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('show')">
              <span v-text="$t('gaApp.formField.show')">Show</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'show'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('isCardField')">
              <span v-text="$t('gaApp.formField.isCardField')">Is Card Field</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isCardField'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('isOaField')">
              <span v-text="$t('gaApp.formField.isOaField')">Is Oa Field</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'isOaField'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('orderNum')">
              <span v-text="$t('gaApp.formField.orderNum')">Order Num</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'orderNum'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('workflowTemplate.id')">
              <span v-text="$t('gaApp.formField.workflowTemplate')">Workflow Template</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'workflowTemplate.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="formField in formFields" :key="formField.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'FormFieldView', params: { formFieldId: formField.id } }">{{ formField.id }}</router-link>
            </td>
            <td>{{ formField.fieldName }}</td>
            <td>{{ formField.oaId }}</td>
            <td>{{ formField.fielddbtype }}</td>
            <td>{{ formField.labelName }}</td>
            <td>{{ formField.detailtable }}</td>
            <td>{{ formField.show }}</td>
            <td>{{ formField.isCardField }}</td>
            <td>{{ formField.isOaField }}</td>
            <td>{{ formField.orderNum }}</td>
            <td>
              <div v-if="formField.workflowTemplate">
                <router-link :to="{ name: 'WorkflowTemplateView', params: { workflowTemplateId: formField.workflowTemplate.id } }">{{
                  formField.workflowTemplate.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'FormFieldView', params: { formFieldId: formField.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'FormFieldEdit', params: { formFieldId: formField.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(formField)"
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
        ><span id="gaApp.formField.delete.question" data-cy="formFieldDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-formField-heading" v-text="$t('gaApp.formField.delete.question', { id: removeId })">
          Are you sure you want to delete this Form Field?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-formField"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeFormField()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="formFields && formFields.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./form-field.component.ts"></script>
