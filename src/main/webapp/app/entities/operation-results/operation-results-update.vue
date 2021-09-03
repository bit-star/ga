<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gaApp.operationResults.home.createOrEditLabel"
          data-cy="OperationResultsCreateUpdateHeading"
          v-text="$t('gaApp.operationResults.home.createOrEditLabel')"
        >
          Create or edit a OperationResults
        </h2>
        <div>
          <div class="form-group" v-if="operationResults.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="operationResults.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.operationResults.operationType')" for="operation-results-operationType"
              >Operation Type</label
            >
            <select
              class="form-control"
              name="operationType"
              :class="{ valid: !$v.operationResults.operationType.$invalid, invalid: $v.operationResults.operationType.$invalid }"
              v-model="$v.operationResults.operationType.$model"
              id="operation-results-operationType"
              data-cy="operationType"
            >
              <option value="Agree" v-bind:label="$t('gaApp.OperationType.Agree')">Agree</option>
              <option value="Refuse" v-bind:label="$t('gaApp.OperationType.Refuse')">Refuse</option>
              <option value="Comment" v-bind:label="$t('gaApp.OperationType.Comment')">Comment</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.operationResults.time')" for="operation-results-time">Time</label>
            <div class="d-flex">
              <input
                id="operation-results-time"
                data-cy="time"
                type="datetime-local"
                class="form-control"
                name="time"
                :class="{ valid: !$v.operationResults.time.$invalid, invalid: $v.operationResults.time.$invalid }"
                :value="convertDateTimeFromServer($v.operationResults.time.$model)"
                @change="updateInstantField('time', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.operationResults.text')" for="operation-results-text">Text</label>
            <input
              type="text"
              class="form-control"
              name="text"
              id="operation-results-text"
              data-cy="text"
              :class="{ valid: !$v.operationResults.text.$invalid, invalid: $v.operationResults.text.$invalid }"
              v-model="$v.operationResults.text.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.operationResults.operationSource')" for="operation-results-operationSource"
              >Operation Source</label
            >
            <select
              class="form-control"
              name="operationSource"
              :class="{ valid: !$v.operationResults.operationSource.$invalid, invalid: $v.operationResults.operationSource.$invalid }"
              v-model="$v.operationResults.operationSource.$model"
              id="operation-results-operationSource"
              data-cy="operationSource"
            >
              <option value="Card" v-bind:label="$t('gaApp.OperationSource.Card')">Card</option>
              <option value="OA" v-bind:label="$t('gaApp.OperationSource.OA')">OA</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.operationResults.ddUser')" for="operation-results-ddUser">Dd User</label>
            <select class="form-control" id="operation-results-ddUser" data-cy="ddUser" name="ddUser" v-model="operationResults.ddUser">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  operationResults.ddUser && ddUserOption.id === operationResults.ddUser.id ? operationResults.ddUser : ddUserOption
                "
                v-for="ddUserOption in ddUsers"
                :key="ddUserOption.id"
              >
                {{ ddUserOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.operationResults.publicCardData')" for="operation-results-publicCardData"
              >Public Card Data</label
            >
            <select
              class="form-control"
              id="operation-results-publicCardData"
              data-cy="publicCardData"
              name="publicCardData"
              v-model="operationResults.publicCardData"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  operationResults.publicCardData && publicCardDataOption.id === operationResults.publicCardData.id
                    ? operationResults.publicCardData
                    : publicCardDataOption
                "
                v-for="publicCardDataOption in publicCardData"
                :key="publicCardDataOption.id"
              >
                {{ publicCardDataOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.operationResults.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./operation-results-update.component.ts"></script>
