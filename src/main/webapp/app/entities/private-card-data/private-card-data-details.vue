<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="privateCardData">
        <h2 class="jh-entity-heading" data-cy="privateCardDataDetailsHeading">
          <span v-text="$t('gaApp.privateCardData.detail.title')">PrivateCardData</span> {{ privateCardData.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('gaApp.privateCardData.name')">Name</span>
          </dt>
          <dd>
            <span>{{ privateCardData.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.privateCardData.fee')">Fee</span>
          </dt>
          <dd>
            <span>{{ privateCardData.fee }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.privateCardData.reason')">Reason</span>
          </dt>
          <dd>
            <span>{{ privateCardData.reason }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.privateCardData.itemType')">Item Type</span>
          </dt>
          <dd>
            <span>{{ privateCardData.itemType }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.privateCardData.typesOfFee')">Types Of Fee</span>
          </dt>
          <dd>
            <span>{{ privateCardData.typesOfFee }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.privateCardData.agree')">Agree</span>
          </dt>
          <dd>
            <span>{{ privateCardData.agree }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.privateCardData.publicCardData')">Public Card Data</span>
          </dt>
          <dd>
            <div v-if="privateCardData.publicCardData">
              <router-link :to="{ name: 'PublicCardDataView', params: { publicCardDataId: privateCardData.publicCardData.id } }">{{
                privateCardData.publicCardData.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('gaApp.privateCardData.ddUser')">Dd User</span>
          </dt>
          <dd>
            <div v-if="privateCardData.ddUser">
              <router-link :to="{ name: 'DdUserView', params: { ddUserId: privateCardData.ddUser.id } }">{{
                privateCardData.ddUser.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="privateCardData.id"
          :to="{ name: 'PrivateCardDataEdit', params: { privateCardDataId: privateCardData.id } }"
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

<script lang="ts" src="./private-card-data-details.component.ts"></script>
