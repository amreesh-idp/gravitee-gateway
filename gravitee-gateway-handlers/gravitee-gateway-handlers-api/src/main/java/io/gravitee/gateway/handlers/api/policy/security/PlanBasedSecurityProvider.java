/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.gateway.handlers.api.policy.security;

import io.gravitee.gateway.api.ExecutionContext;
import io.gravitee.gateway.api.Request;
import io.gravitee.gateway.handlers.api.definition.Plan;
import io.gravitee.gateway.security.core.SecurityPolicy;
import io.gravitee.gateway.security.core.SecurityProvider;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class PlanBasedSecurityProvider implements SecurityProvider {

    private final SecurityProvider wrapper;
    private final Plan plan;

    public PlanBasedSecurityProvider(final SecurityProvider wrapper, final Plan plan) {
        this.wrapper = wrapper;
        this.plan = plan;
    }

    @Override
    public boolean canHandle(Request request) {
        return wrapper.canHandle(request);
    }

    @Override
    public String name() {
        return wrapper.name();
    }

    @Override
    public int order() {
        return wrapper.order();
    }

    @Override
    public SecurityPolicy create(ExecutionContext executionContext) {
        executionContext.setAttribute(ExecutionContext.ATTR_PLAN, plan.getId());
        return wrapper.create(executionContext);
    }
}