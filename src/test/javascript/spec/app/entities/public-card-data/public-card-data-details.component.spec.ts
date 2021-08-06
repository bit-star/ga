/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PublicCardDataDetailComponent from '@/entities/public-card-data/public-card-data-details.vue';
import PublicCardDataClass from '@/entities/public-card-data/public-card-data-details.component';
import PublicCardDataService from '@/entities/public-card-data/public-card-data.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PublicCardData Management Detail Component', () => {
    let wrapper: Wrapper<PublicCardDataClass>;
    let comp: PublicCardDataClass;
    let publicCardDataServiceStub: SinonStubbedInstance<PublicCardDataService>;

    beforeEach(() => {
      publicCardDataServiceStub = sinon.createStubInstance<PublicCardDataService>(PublicCardDataService);

      wrapper = shallowMount<PublicCardDataClass>(PublicCardDataDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { publicCardDataService: () => publicCardDataServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPublicCardData = { id: 123 };
        publicCardDataServiceStub.find.resolves(foundPublicCardData);

        // WHEN
        comp.retrievePublicCardData(123);
        await comp.$nextTick();

        // THEN
        expect(comp.publicCardData).toBe(foundPublicCardData);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPublicCardData = { id: 123 };
        publicCardDataServiceStub.find.resolves(foundPublicCardData);

        // WHEN
        comp.beforeRouteEnter({ params: { publicCardDataId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.publicCardData).toBe(foundPublicCardData);
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
