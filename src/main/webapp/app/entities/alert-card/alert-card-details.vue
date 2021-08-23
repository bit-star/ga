<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="alertCard">
        <h2 class="jh-entity-heading" data-cy="alertCardDetailsHeading">
          <span v-text="$t('gaApp.alertCard.detail.title')">AlertCard</span> {{ alertCard.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('gaApp.alertCard.text')">Text</span>
          </dt>
          <dd>
            <span>{{ alertCard.text }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.alertCard.userId')">User Id</span>
          </dt>
          <dd>
            <span>{{ alertCard.userId }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.alertCard.publicCardData')">Public Card Data</span>
          </dt>
          <dd>
            <div v-if="alertCard.publicCardData">
              <router-link :to="{ name: 'PublicCardDataView', params: { publicCardDataId: alertCard.publicCardData.id } }">{{
                alertCard.publicCardData.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="alertCard.id"
          :to="{ name: 'AlertCardEdit', params: { alertCardId: alertCard.id } }"
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

<script lang="ts" src="./alert-card-details.component.ts"></script>
