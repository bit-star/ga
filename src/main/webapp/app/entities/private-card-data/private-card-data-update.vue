<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gaApp.privateCardData.home.createOrEditLabel"
          data-cy="PrivateCardDataCreateUpdateHeading"
          v-text="$t('gaApp.privateCardData.home.createOrEditLabel')"
        >
          Create or edit a PrivateCardData
        </h2>
        <div>
          <div class="form-group" v-if="privateCardData.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="privateCardData.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.privateCardData.agree')" for="private-card-data-agree">Agree</label>
            <input
              type="checkbox"
              class="form-check"
              name="agree"
              id="private-card-data-agree"
              data-cy="agree"
              :class="{ valid: !$v.privateCardData.agree.$invalid, invalid: $v.privateCardData.agree.$invalid }"
              v-model="$v.privateCardData.agree.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.privateCardData.finish')" for="private-card-data-finish">Finish</label>
            <input
              type="text"
              class="form-control"
              name="finish"
              id="private-card-data-finish"
              data-cy="finish"
              :class="{ valid: !$v.privateCardData.finish.$invalid, invalid: $v.privateCardData.finish.$invalid }"
              v-model="$v.privateCardData.finish.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.privateCardData.authority')" for="private-card-data-authority"
              >Authority</label
            >
            <input
              type="text"
              class="form-control"
              name="authority"
              id="private-card-data-authority"
              data-cy="authority"
              :class="{ valid: !$v.privateCardData.authority.$invalid, invalid: $v.privateCardData.authority.$invalid }"
              v-model="$v.privateCardData.authority.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.privateCardData.createdByMe')" for="private-card-data-createdByMe"
              >Created By Me</label
            >
            <input
              type="text"
              class="form-control"
              name="createdByMe"
              id="private-card-data-createdByMe"
              data-cy="createdByMe"
              :class="{ valid: !$v.privateCardData.createdByMe.$invalid, invalid: $v.privateCardData.createdByMe.$invalid }"
              v-model="$v.privateCardData.createdByMe.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.privateCardData.variables')" for="private-card-data-variables"
              >Variables</label
            >
            <input
              type="text"
              class="form-control"
              name="variables"
              id="private-card-data-variables"
              data-cy="variables"
              :class="{ valid: !$v.privateCardData.variables.$invalid, invalid: $v.privateCardData.variables.$invalid }"
              v-model="$v.privateCardData.variables.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.privateCardData.updateTime')" for="private-card-data-updateTime"
              >Update Time</label
            >
            <div class="d-flex">
              <input
                id="private-card-data-updateTime"
                data-cy="updateTime"
                type="datetime-local"
                class="form-control"
                name="updateTime"
                :class="{ valid: !$v.privateCardData.updateTime.$invalid, invalid: $v.privateCardData.updateTime.$invalid }"
                :value="convertDateTimeFromServer($v.privateCardData.updateTime.$model)"
                @change="updateInstantField('updateTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.privateCardData.publicCardData')" for="private-card-data-publicCardData"
              >Public Card Data</label
            >
            <select
              class="form-control"
              id="private-card-data-publicCardData"
              data-cy="publicCardData"
              name="publicCardData"
              v-model="privateCardData.publicCardData"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  privateCardData.publicCardData && publicCardDataOption.id === privateCardData.publicCardData.id
                    ? privateCardData.publicCardData
                    : publicCardDataOption
                "
                v-for="publicCardDataOption in publicCardData"
                :key="publicCardDataOption.id"
              >
                {{ publicCardDataOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.privateCardData.ddUser')" for="private-card-data-ddUser">Dd User</label>
            <select class="form-control" id="private-card-data-ddUser" data-cy="ddUser" name="ddUser" v-model="privateCardData.ddUser">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  privateCardData.ddUser && ddUserOption.id === privateCardData.ddUser.id ? privateCardData.ddUser : ddUserOption
                "
                v-for="ddUserOption in ddUsers"
                :key="ddUserOption.id"
              >
                {{ ddUserOption.id }}
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
            :disabled="$v.privateCardData.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./private-card-data-update.component.ts"></script>
