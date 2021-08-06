/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import GroupMembersUpdateComponent from '@/entities/group-members/group-members-update.vue';
import GroupMembersClass from '@/entities/group-members/group-members-update.component';
import GroupMembersService from '@/entities/group-members/group-members.service';

import ConversationService from '@/entities/conversation/conversation.service';

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
  describe('GroupMembers Management Update Component', () => {
    let wrapper: Wrapper<GroupMembersClass>;
    let comp: GroupMembersClass;
    let groupMembersServiceStub: SinonStubbedInstance<GroupMembersService>;

    beforeEach(() => {
      groupMembersServiceStub = sinon.createStubInstance<GroupMembersService>(GroupMembersService);

      wrapper = shallowMount<GroupMembersClass>(GroupMembersUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          groupMembersService: () => groupMembersServiceStub,

          conversationService: () => new ConversationService(),

          ddUserService: () => new DdUserService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.groupMembers = entity;
        groupMembersServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(groupMembersServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.groupMembers = entity;
        groupMembersServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(groupMembersServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundGroupMembers = { id: 123 };
        groupMembersServiceStub.find.resolves(foundGroupMembers);
        groupMembersServiceStub.retrieve.resolves([foundGroupMembers]);

        // WHEN
        comp.beforeRouteEnter({ params: { groupMembersId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.groupMembers).toBe(foundGroupMembers);
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
