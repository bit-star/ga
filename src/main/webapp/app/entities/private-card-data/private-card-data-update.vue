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
            <label class="form-control-label" v-text="$t('gaApp.privateCardData.name')" for="private-card-data-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="private-card-data-name"
              data-cy="name"
              :class="{ valid: !$v.privateCardData.name.$invalid, invalid: $v.privateCardData.name.$invalid }"
              v-model="$v.privateCardData.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.privateCardData.fee')" for="private-card-data-fee">Fee</label>
            <input
              type="text"
              class="form-control"
              name="fee"
              id="private-card-data-fee"
              data-cy="fee"
              :class="{ valid: !$v.privateCardData.fee.$invalid, invalid: $v.privateCardData.fee.$invalid }"
              v-model="$v.privateCardData.fee.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.privateCardData.reason')" for="private-card-data-reason">Reason</label>
            <input
              type="text"
              class="form-control"
              name="reason"
              id="private-card-data-reason"
              data-cy="reason"
              :class="{ valid: !$v.privateCardData.reason.$invalid, invalid: $v.privateCardData.reason.$invalid }"
              v-model="$v.privateCardData.reason.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.privateCardData.itemType')" for="private-card-data-itemType"
              >Item Type</label
            >
            <input
              type="text"
              class="form-control"
              name="itemType"
              id="private-card-data-itemType"
              data-cy="itemType"
              :class="{ valid: !$v.privateCardData.itemType.$invalid, invalid: $v.privateCardData.itemType.$invalid }"
              v-model="$v.privateCardData.itemType.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('gaApp.privateCardData.typesOfFee')" for="private-card-data-typesOfFee"
              >Types Of Fee</label
            >
            <input
              type="text"
              class="form-control"
              name="typesOfFee"
              id="private-card-data-typesOfFee"
              data-cy="typesOfFee"
              :class="{ valid: !$v.privateCardData.typesOfFee.$invalid, invalid: $v.privateCardData.typesOfFee.$invalid }"
              v-model="$v.privateCardData.typesOfFee.$model"
            />
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
