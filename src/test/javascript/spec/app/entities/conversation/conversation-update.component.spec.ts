/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ConversationUpdateComponent from '@/entities/conversation/conversation-update.vue';
import ConversationClass from '@/entities/conversation/conversation-update.component';
import ConversationService from '@/entities/conversation/conversation.service';

import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';

import DdUserService from '@/entities/dd-user/dd-user.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Conversation Management Update Component', () => {
    let wrapper: Wrapper<ConversationClass>;
    let comp: ConversationClass;
    let conversationServiceStub: SinonStubbedInstance<ConversationService>;

    beforeEach(() => {
      conversationServiceStub = sinon.createStubInstance<ConversationService>(ConversationService);

      wrapper = shallowMount<ConversationClass>(ConversationUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          conversationService: () => conversationServiceStub,

          publicCardDataService: () => new PublicCardDataService(),

          ddUserService: () => new DdUserService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 'ABC' };
        comp.conversation = entity;
        conversationServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(conversationServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.conversation = entity;
        conversationServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(conversationServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundConversation = { id: 'ABC' };
        conversationServiceStub.find.resolves(foundConversation);
        conversationServiceStub.retrieve.resolves([foundConversation]);

        // WHEN
        comp.beforeRouteEnter({ params: { conversationId: 'ABC' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.conversation).toBe(foundConversation);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
