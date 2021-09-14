/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import TopboxesUpdateComponent from '@/entities/topboxes/topboxes-update.vue';
import TopboxesClass from '@/entities/topboxes/topboxes-update.component';
import TopboxesService from '@/entities/topboxes/topboxes.service';

import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';

import ConversationService from '@/entities/conversation/conversation.service';

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
  describe('Topboxes Management Update Component', () => {
    let wrapper: Wrapper<TopboxesClass>;
    let comp: TopboxesClass;
    let topboxesServiceStub: SinonStubbedInstance<TopboxesService>;

    beforeEach(() => {
      topboxesServiceStub = sinon.createStubInstance<TopboxesService>(TopboxesService);

      wrapper = shallowMount<TopboxesClass>(TopboxesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          topboxesService: () => topboxesServiceStub,

          publicCardDataService: () => new PublicCardDataService(),

          conversationService: () => new ConversationService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        comp.topboxes = entity;
        topboxesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(topboxesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.topboxes = entity;
        topboxesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(topboxesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTopboxes = { id: '9fec3727-3421-4967-b213-ba36557ca194' };
        topboxesServiceStub.find.resolves(foundTopboxes);
        topboxesServiceStub.retrieve.resolves([foundTopboxes]);

        // WHEN
        comp.beforeRouteEnter({ params: { topboxesId: '9fec3727-3421-4967-b213-ba36557ca194' } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.topboxes).toBe(foundTopboxes);
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
