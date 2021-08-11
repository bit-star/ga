/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ConfirmCardUpdateComponent from '@/entities/confirm-card/confirm-card-update.vue';
import ConfirmCardClass from '@/entities/confirm-card/confirm-card-update.component';
import ConfirmCardService from '@/entities/confirm-card/confirm-card.service';

import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';

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
  describe('ConfirmCard Management Update Component', () => {
    let wrapper: Wrapper<ConfirmCardClass>;
    let comp: ConfirmCardClass;
    let confirmCardServiceStub: SinonStubbedInstance<ConfirmCardService>;

    beforeEach(() => {
      confirmCardServiceStub = sinon.createStubInstance<ConfirmCardService>(ConfirmCardService);

      wrapper = shallowMount<ConfirmCardClass>(ConfirmCardUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          confirmCardService: () => confirmCardServiceStub,

          publicCardDataService: () => new PublicCardDataService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.confirmCard = entity;
        confirmCardServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(confirmCardServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.confirmCard = entity;
        confirmCardServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(confirmCardServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundConfirmCard = { id: 123 };
        confirmCardServiceStub.find.resolves(foundConfirmCard);
        confirmCardServiceStub.retrieve.resolves([foundConfirmCard]);

        // WHEN
        comp.beforeRouteEnter({ params: { confirmCardId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.confirmCard).toBe(foundConfirmCard);
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
