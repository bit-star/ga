<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="confirmCard">
        <h2 class="jh-entity-heading" data-cy="confirmCardDetailsHeading">
          <span v-text="$t('gaApp.confirmCard.detail.title')">ConfirmCard</span> {{ confirmCard.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('gaApp.confirmCard.text')">Text</span>
          </dt>
          <dd>
            <span>{{ confirmCard.text }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.confirmCard.finish')">Finish</span>
          </dt>
          <dd>
            <span>{{ confirmCard.finish }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.confirmCard.userId')">User Id</span>
          </dt>
          <dd>
            <span>{{ confirmCard.userId }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.confirmCard.link')">Link</span>
          </dt>
          <dd>
            <span>{{ confirmCard.link }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.confirmCard.md1')">Md 1</span>
          </dt>
          <dd>
            <span>{{ confirmCard.md1 }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.confirmCard.publicCardData')">Public Card Data</span>
          </dt>
          <dd>
            <div v-if="confirmCard.publicCardData">
              <router-link :to="{ name: 'PublicCardDataView', params: { publicCardDataId: confirmCard.publicCardData.id } }">{{
                confirmCard.publicCardData.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="confirmCard.id"
          :to="{ name: 'ConfirmCardEdit', params: { confirmCardId: confirmCard.id } }"
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

<script lang="ts" src="./confirm-card-details.component.ts"></script>
