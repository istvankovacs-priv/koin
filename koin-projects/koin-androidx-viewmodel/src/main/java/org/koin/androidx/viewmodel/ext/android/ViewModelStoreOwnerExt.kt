/*
 * Copyright 2017-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.koin.androidx.viewmodel.ext.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import org.koin.androidx.viewmodel.ViewModelOwner.Companion.from
import org.koin.androidx.viewmodel.koin.getViewModel
import org.koin.androidx.viewmodel.scope.BundleDefinition
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

/**
 * ViewModelStoreOwner extensions to help for ViewModel
 *
 * @author Arnaud Giuliani
 */
inline fun <reified T : ViewModel> ViewModelStoreOwner.viewModel(
    qualifier: Qualifier? = null,
    noinline state: BundleDefinition? = null,
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> {
    return lazy(mode) {
        getViewModel<T>(qualifier, state, parameters)
    }
}

fun <T : ViewModel> ViewModelStoreOwner.viewModel(
    qualifier: Qualifier? = null,
    state: BundleDefinition? = null,
    clazz: KClass<T>,
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED,
    parameters: ParametersDefinition? = null
): Lazy<T> {
    return lazy(mode) { getViewModel(qualifier, state, clazz, parameters) }
}

inline fun <reified T : ViewModel> ViewModelStoreOwner.getViewModel(
    qualifier: Qualifier? = null,
    noinline state: BundleDefinition? = null,
    noinline parameters: ParametersDefinition? = null
): T {
    return getViewModel(qualifier, state, T::class, parameters)
}

fun <T : ViewModel> ViewModelStoreOwner.getViewModel(
    qualifier: Qualifier? = null,
    state: BundleDefinition? = null,
    clazz: KClass<T>,
    parameters: ParametersDefinition? = null
): T {
    return GlobalContext.get().getViewModel(qualifier, state, { from(this, null) }, clazz, parameters)
}