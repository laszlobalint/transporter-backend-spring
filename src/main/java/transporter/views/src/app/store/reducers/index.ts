import * as fromTransport from '../reducers/transport.reducer';
import * as fromRouter from '../actions';
import {
    ActionReducerMap,
    Action,
    ActionReducer,
    createFeatureSelector,
    createSelector,
} from '@ngrx/store';

export interface CoreState {
    transports: fromTransport.TransportState;
}

export const reducers: ActionReducerMap<CoreState, Action> = {
    transports: fromTransport.reducer,
};

export function resetStateMetaReducer(
    reducer: ActionReducer<CoreState>
): (state: CoreState | undefined, action: Action) => CoreState {
    return function resetReducer(
        state: CoreState | undefined,
        action: Action
    ): CoreState {
        return reducer(
            action.type === fromRouter.RouterActionTypes.Back
                ? {
                      ...state,
                      transports: fromTransport.initialState,
                  }
                : state,
            action
        );
    };
}

export const selectCoreState = createFeatureSelector<CoreState>('core');

/* Selectors for Transport */

export const selectTransports = createSelector(
    selectCoreState,
    state => state.transports
);
