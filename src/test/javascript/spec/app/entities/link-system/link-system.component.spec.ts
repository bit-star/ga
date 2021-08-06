/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import LinkSystemComponent from '@/entities/link-system/link-system.vue';
import LinkSystemClass from '@/entities/link-system/link-system.component';
import LinkSystemService from '@/entities/link-system/link-system.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('LinkSystem Management Component', () => {
    let wrapper: Wrapper<LinkSystemClass>;
    let comp: LinkSystemClass;
    let linkSystemServiceStub: SinonStubbedInstance<LinkSystemService>;

    beforeEach(() => {
      linkSystemServiceStub = sinon.createStubInstance<LinkSystemService>(LinkSystemService);
      linkSystemServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<LinkSystemClass>(LinkSystemComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          linkSystemService: () => linkSystemServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      linkSystemServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllLinkSystems();
      await comp.$nextTick();

      // THEN
      expect(linkSystemServiceStub.retrieve.called).toBeTruthy();
      expect(comp.linkSystems[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      linkSystemServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeLinkSystem();
      await comp.$nextTick();

      // THEN
      expect(linkSystemServiceStub.delete.called).toBeTruthy();
      expect(linkSystemServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
