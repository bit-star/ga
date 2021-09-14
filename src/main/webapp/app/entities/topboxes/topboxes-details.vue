<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="topboxes">
        <h2 class="jh-entity-heading" data-cy="topboxesDetailsHeading">
          <span v-text="$t('gaApp.topboxes.detail.title')">Topboxes</span> {{ topboxes.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('gaApp.topboxes.text')">Text</span>
          </dt>
          <dd>
            <span>{{ topboxes.text }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.topboxes.link')">Link</span>
          </dt>
          <dd>
            <span>{{ topboxes.link }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.topboxes.cardId')">Card Id</span>
          </dt>
          <dd>
            <span>{{ topboxes.cardId }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.topboxes.auxiliary')">Auxiliary</span>
          </dt>
          <dd>
            <span>{{ topboxes.auxiliary }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.topboxes.open')">Open</span>
          </dt>
          <dd>
            <span>{{ topboxes.open }}</span>
          </dd>
          <dt>
            <span v-text="$t('gaApp.topboxes.conversation')">Conversation</span>
          </dt>
          <dd>
            <div v-if="topboxes.conversation">
              <router-link :to="{ name: 'ConversationView', params: { conversationId: topboxes.conversation.id } }">{{
                topboxes.conversation.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="topboxes.id" :to="{ name: 'TopboxesEdit', params: { topboxesId: topboxes.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./topboxes-details.component.ts"></script>
