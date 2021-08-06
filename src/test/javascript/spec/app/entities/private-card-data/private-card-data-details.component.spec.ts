/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PrivateCardDataDetailComponent from '@/entities/private-card-data/private-card-data-details.vue';
import PrivateCardDataClass from '@/entities/private-card-data/private-card-data-details.component';
import PrivateCardDataService from '@/entities/private-card-data/private-card-data.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PrivateCardData Management Detail Component', () => {
    let wrapper: Wrapper<PrivateCardDataClass>;
    let comp: PrivateCardDataClass;
    let privateCardDataServiceStub: SinonStubbedInstance<PrivateCardDataService>;

    beforeEach(() => {
      privateCardDataServiceStub = sinon.createStubInstance<PrivateCardDataService>(PrivateCardDataService);

      wrapper = shallowMount<PrivateCardDataClass>(PrivateCardDataDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { privateCardDataService: () => privateCardDataServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPrivateCardData = { id: 123 };
        privateCardDataServiceStub.find.resolves(foundPrivateCardData);

        // WHEN
        comp.retrievePrivateCardData(123);
        await comp.$nextTick();

        // THEN
        expect(comp.privateCardData).toBe(foundPrivateCardData);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPrivateCardData = { id: 123 };
        privateCardDataServiceStub.find.resolves(foundPrivateCardData);

        // WHEN
        comp.beforeRouteEnter({ params: { privateCardDataId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.privateCardData).toBe(foundPrivateCardData);
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
