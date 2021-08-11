/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ConfirmCardDetailComponent from '@/entities/confirm-card/confirm-card-details.vue';
import ConfirmCardClass from '@/entities/confirm-card/confirm-card-details.component';
import ConfirmCardService from '@/entities/confirm-card/confirm-card.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ConfirmCard Management Detail Component', () => {
    let wrapper: Wrapper<ConfirmCardClass>;
    let comp: ConfirmCardClass;
    let confirmCardServiceStub: SinonStubbedInstance<ConfirmCardService>;

    beforeEach(() => {
      confirmCardServiceStub = sinon.createStubInstance<ConfirmCardService>(ConfirmCardService);

      wrapper = shallowMount<ConfirmCardClass>(ConfirmCardDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { confirmCardService: () => confirmCardServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundConfirmCard = { id: 123 };
        confirmCardServiceStub.find.resolves(foundConfirmCard);

        // WHEN
        comp.retrieveConfirmCard(123);
        await comp.$nextTick();

        // THEN
        expect(comp.confirmCard).toBe(foundConfirmCard);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundConfirmCard = { id: 123 };
        confirmCardServiceStub.find.resolves(foundConfirmCard);

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
