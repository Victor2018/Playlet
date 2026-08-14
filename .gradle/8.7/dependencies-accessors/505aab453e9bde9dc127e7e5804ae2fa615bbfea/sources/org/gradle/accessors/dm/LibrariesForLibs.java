package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.MinimalExternalModuleDependency;
import org.gradle.plugin.use.PluginDependency;
import org.gradle.api.artifacts.ExternalModuleDependencyBundle;
import org.gradle.api.artifacts.MutableVersionConstraint;
import org.gradle.api.provider.Provider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ProviderFactory;
import org.gradle.api.internal.catalog.AbstractExternalDependencyFactory;
import org.gradle.api.internal.catalog.DefaultVersionCatalog;
import java.util.Map;
import org.gradle.api.internal.attributes.ImmutableAttributesFactory;
import org.gradle.api.internal.artifacts.dsl.CapabilityNotationParser;
import javax.inject.Inject;

/**
 * A catalog of dependencies accessible via the {@code libs} extension.
 */
@NonNullApi
public class LibrariesForLibs extends AbstractExternalDependencyFactory {

    private final AbstractExternalDependencyFactory owner = this;
    private final AndroidLibraryAccessors laccForAndroidLibraryAccessors = new AndroidLibraryAccessors(owner);
    private final AndroidxLibraryAccessors laccForAndroidxLibraryAccessors = new AndroidxLibraryAccessors(owner);
    private final ArouterLibraryAccessors laccForArouterLibraryAccessors = new ArouterLibraryAccessors(owner);
    private final BuglyLibraryAccessors laccForBuglyLibraryAccessors = new BuglyLibraryAccessors(owner);
    private final CoroutinesLibraryAccessors laccForCoroutinesLibraryAccessors = new CoroutinesLibraryAccessors(owner);
    private final ExoplayerLibraryAccessors laccForExoplayerLibraryAccessors = new ExoplayerLibraryAccessors(owner);
    private final GlideLibraryAccessors laccForGlideLibraryAccessors = new GlideLibraryAccessors(owner);
    private final KotlinLibraryAccessors laccForKotlinLibraryAccessors = new KotlinLibraryAccessors(owner);
    private final KspLibraryAccessors laccForKspLibraryAccessors = new KspLibraryAccessors(owner);
    private final LifecycleLibraryAccessors laccForLifecycleLibraryAccessors = new LifecycleLibraryAccessors(owner);
    private final LottieLibraryAccessors laccForLottieLibraryAccessors = new LottieLibraryAccessors(owner);
    private final MultidexLibraryAccessors laccForMultidexLibraryAccessors = new MultidexLibraryAccessors(owner);
    private final Okhttp3LibraryAccessors laccForOkhttp3LibraryAccessors = new Okhttp3LibraryAccessors(owner);
    private final PagingLibraryAccessors laccForPagingLibraryAccessors = new PagingLibraryAccessors(owner);
    private final PictureLibraryAccessors laccForPictureLibraryAccessors = new PictureLibraryAccessors(owner);
    private final RetrofitLibraryAccessors laccForRetrofitLibraryAccessors = new RetrofitLibraryAccessors(owner);
    private final RoomLibraryAccessors laccForRoomLibraryAccessors = new RoomLibraryAccessors(owner);
    private final UmengLibraryAccessors laccForUmengLibraryAccessors = new UmengLibraryAccessors(owner);
    private final VictorLibraryAccessors laccForVictorLibraryAccessors = new VictorLibraryAccessors(owner);
    private final VideoLibraryAccessors laccForVideoLibraryAccessors = new VideoLibraryAccessors(owner);
    private final WechatLibraryAccessors laccForWechatLibraryAccessors = new WechatLibraryAccessors(owner);
    private final WorkLibraryAccessors laccForWorkLibraryAccessors = new WorkLibraryAccessors(owner);
    private final ZxingLibraryAccessors laccForZxingLibraryAccessors = new ZxingLibraryAccessors(owner);
    private final VersionAccessors vaccForVersionAccessors = new VersionAccessors(providers, config);
    private final BundleAccessors baccForBundleAccessors = new BundleAccessors(objects, providers, config, attributesFactory, capabilityNotationParser);
    private final PluginAccessors paccForPluginAccessors = new PluginAccessors(providers, config);

    @Inject
    public LibrariesForLibs(DefaultVersionCatalog config, ProviderFactory providers, ObjectFactory objects, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) {
        super(config, providers, objects, attributesFactory, capabilityNotationParser);
    }

    /**
     * Dependency provider for <b>junit</b> with <b>junit:junit</b> coordinates and
     * with version reference <b>junit</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getJunit() {
        return create("junit");
    }

    /**
     * Dependency provider for <b>material</b> with <b>com.google.android.material:material</b> coordinates and
     * with version reference <b>material</b>
     * <p>
     * This dependency was declared in catalog libs.versions.toml
     */
    public Provider<MinimalExternalModuleDependency> getMaterial() {
        return create("material");
    }

    /**
     * Group of libraries at <b>android</b>
     */
    public AndroidLibraryAccessors getAndroid() {
        return laccForAndroidLibraryAccessors;
    }

    /**
     * Group of libraries at <b>androidx</b>
     */
    public AndroidxLibraryAccessors getAndroidx() {
        return laccForAndroidxLibraryAccessors;
    }

    /**
     * Group of libraries at <b>arouter</b>
     */
    public ArouterLibraryAccessors getArouter() {
        return laccForArouterLibraryAccessors;
    }

    /**
     * Group of libraries at <b>bugly</b>
     */
    public BuglyLibraryAccessors getBugly() {
        return laccForBuglyLibraryAccessors;
    }

    /**
     * Group of libraries at <b>coroutines</b>
     */
    public CoroutinesLibraryAccessors getCoroutines() {
        return laccForCoroutinesLibraryAccessors;
    }

    /**
     * Group of libraries at <b>exoplayer</b>
     */
    public ExoplayerLibraryAccessors getExoplayer() {
        return laccForExoplayerLibraryAccessors;
    }

    /**
     * Group of libraries at <b>glide</b>
     */
    public GlideLibraryAccessors getGlide() {
        return laccForGlideLibraryAccessors;
    }

    /**
     * Group of libraries at <b>kotlin</b>
     */
    public KotlinLibraryAccessors getKotlin() {
        return laccForKotlinLibraryAccessors;
    }

    /**
     * Group of libraries at <b>ksp</b>
     */
    public KspLibraryAccessors getKsp() {
        return laccForKspLibraryAccessors;
    }

    /**
     * Group of libraries at <b>lifecycle</b>
     */
    public LifecycleLibraryAccessors getLifecycle() {
        return laccForLifecycleLibraryAccessors;
    }

    /**
     * Group of libraries at <b>lottie</b>
     */
    public LottieLibraryAccessors getLottie() {
        return laccForLottieLibraryAccessors;
    }

    /**
     * Group of libraries at <b>multidex</b>
     */
    public MultidexLibraryAccessors getMultidex() {
        return laccForMultidexLibraryAccessors;
    }

    /**
     * Group of libraries at <b>okhttp3</b>
     */
    public Okhttp3LibraryAccessors getOkhttp3() {
        return laccForOkhttp3LibraryAccessors;
    }

    /**
     * Group of libraries at <b>paging</b>
     */
    public PagingLibraryAccessors getPaging() {
        return laccForPagingLibraryAccessors;
    }

    /**
     * Group of libraries at <b>picture</b>
     */
    public PictureLibraryAccessors getPicture() {
        return laccForPictureLibraryAccessors;
    }

    /**
     * Group of libraries at <b>retrofit</b>
     */
    public RetrofitLibraryAccessors getRetrofit() {
        return laccForRetrofitLibraryAccessors;
    }

    /**
     * Group of libraries at <b>room</b>
     */
    public RoomLibraryAccessors getRoom() {
        return laccForRoomLibraryAccessors;
    }

    /**
     * Group of libraries at <b>umeng</b>
     */
    public UmengLibraryAccessors getUmeng() {
        return laccForUmengLibraryAccessors;
    }

    /**
     * Group of libraries at <b>victor</b>
     */
    public VictorLibraryAccessors getVictor() {
        return laccForVictorLibraryAccessors;
    }

    /**
     * Group of libraries at <b>video</b>
     */
    public VideoLibraryAccessors getVideo() {
        return laccForVideoLibraryAccessors;
    }

    /**
     * Group of libraries at <b>wechat</b>
     */
    public WechatLibraryAccessors getWechat() {
        return laccForWechatLibraryAccessors;
    }

    /**
     * Group of libraries at <b>work</b>
     */
    public WorkLibraryAccessors getWork() {
        return laccForWorkLibraryAccessors;
    }

    /**
     * Group of libraries at <b>zxing</b>
     */
    public ZxingLibraryAccessors getZxing() {
        return laccForZxingLibraryAccessors;
    }

    /**
     * Group of versions at <b>versions</b>
     */
    public VersionAccessors getVersions() {
        return vaccForVersionAccessors;
    }

    /**
     * Group of bundles at <b>bundles</b>
     */
    public BundleAccessors getBundles() {
        return baccForBundleAccessors;
    }

    /**
     * Group of plugins at <b>plugins</b>
     */
    public PluginAccessors getPlugins() {
        return paccForPluginAccessors;
    }

    public static class AndroidLibraryAccessors extends SubDependencyFactory {

        public AndroidLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>desugarJdkLibs</b> with <b>com.android.tools:desugar_jdk_libs</b> coordinates and
         * with version reference <b>androidDesugarJdkLibs</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getDesugarJdkLibs() {
            return create("android.desugarJdkLibs");
        }

        /**
         * Dependency provider for <b>gradlePlugin</b> with <b>com.android.tools.build:gradle</b> coordinates and
         * with version reference <b>agp</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGradlePlugin() {
            return create("android.gradlePlugin");
        }

    }

    public static class AndroidxLibraryAccessors extends SubDependencyFactory {
        private final AndroidxActivityLibraryAccessors laccForAndroidxActivityLibraryAccessors = new AndroidxActivityLibraryAccessors(owner);
        private final AndroidxCoreLibraryAccessors laccForAndroidxCoreLibraryAccessors = new AndroidxCoreLibraryAccessors(owner);
        private final AndroidxEspressoLibraryAccessors laccForAndroidxEspressoLibraryAccessors = new AndroidxEspressoLibraryAccessors(owner);
        private final AndroidxFragmentLibraryAccessors laccForAndroidxFragmentLibraryAccessors = new AndroidxFragmentLibraryAccessors(owner);
        private final AndroidxNavigationLibraryAccessors laccForAndroidxNavigationLibraryAccessors = new AndroidxNavigationLibraryAccessors(owner);

        public AndroidxLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>appcompat</b> with <b>androidx.appcompat:appcompat</b> coordinates and
         * with version reference <b>appcompat</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAppcompat() {
            return create("androidx.appcompat");
        }

        /**
         * Dependency provider for <b>constraintlayout</b> with <b>androidx.constraintlayout:constraintlayout</b> coordinates and
         * with version reference <b>constraintlayout</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getConstraintlayout() {
            return create("androidx.constraintlayout");
        }

        /**
         * Dependency provider for <b>junit</b> with <b>androidx.test.ext:junit</b> coordinates and
         * with version reference <b>junitVersion</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJunit() {
            return create("androidx.junit");
        }

        /**
         * Dependency provider for <b>swiperefreshlayout</b> with <b>androidx.swiperefreshlayout:swiperefreshlayout</b> coordinates and
         * with version reference <b>swiperefreshlayout</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSwiperefreshlayout() {
            return create("androidx.swiperefreshlayout");
        }

        /**
         * Group of libraries at <b>androidx.activity</b>
         */
        public AndroidxActivityLibraryAccessors getActivity() {
            return laccForAndroidxActivityLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.core</b>
         */
        public AndroidxCoreLibraryAccessors getCore() {
            return laccForAndroidxCoreLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.espresso</b>
         */
        public AndroidxEspressoLibraryAccessors getEspresso() {
            return laccForAndroidxEspressoLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.fragment</b>
         */
        public AndroidxFragmentLibraryAccessors getFragment() {
            return laccForAndroidxFragmentLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.navigation</b>
         */
        public AndroidxNavigationLibraryAccessors getNavigation() {
            return laccForAndroidxNavigationLibraryAccessors;
        }

    }

    public static class AndroidxActivityLibraryAccessors extends SubDependencyFactory {

        public AndroidxActivityLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.activity:activity-ktx</b> coordinates and
         * with version reference <b>activity.ktx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("androidx.activity.ktx");
        }

    }

    public static class AndroidxCoreLibraryAccessors extends SubDependencyFactory {

        public AndroidxCoreLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.core:core-ktx</b> coordinates and
         * with version reference <b>core.ktx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("androidx.core.ktx");
        }

    }

    public static class AndroidxEspressoLibraryAccessors extends SubDependencyFactory {

        public AndroidxEspressoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>androidx.test.espresso:espresso-core</b> coordinates and
         * with version reference <b>espressoCore</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("androidx.espresso.core");
        }

    }

    public static class AndroidxFragmentLibraryAccessors extends SubDependencyFactory {

        public AndroidxFragmentLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.fragment:fragment-ktx</b> coordinates and
         * with version reference <b>fragment.ktx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("androidx.fragment.ktx");
        }

    }

    public static class AndroidxNavigationLibraryAccessors extends SubDependencyFactory {
        private final AndroidxNavigationFragmentLibraryAccessors laccForAndroidxNavigationFragmentLibraryAccessors = new AndroidxNavigationFragmentLibraryAccessors(owner);
        private final AndroidxNavigationUiLibraryAccessors laccForAndroidxNavigationUiLibraryAccessors = new AndroidxNavigationUiLibraryAccessors(owner);

        public AndroidxNavigationLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>androidx.navigation.fragment</b>
         */
        public AndroidxNavigationFragmentLibraryAccessors getFragment() {
            return laccForAndroidxNavigationFragmentLibraryAccessors;
        }

        /**
         * Group of libraries at <b>androidx.navigation.ui</b>
         */
        public AndroidxNavigationUiLibraryAccessors getUi() {
            return laccForAndroidxNavigationUiLibraryAccessors;
        }

    }

    public static class AndroidxNavigationFragmentLibraryAccessors extends SubDependencyFactory {

        public AndroidxNavigationFragmentLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.navigation:navigation-fragment-ktx</b> coordinates and
         * with version reference <b>navigationFragmentKtx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("androidx.navigation.fragment.ktx");
        }

    }

    public static class AndroidxNavigationUiLibraryAccessors extends SubDependencyFactory {

        public AndroidxNavigationUiLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.navigation:navigation-ui-ktx</b> coordinates and
         * with version reference <b>navigationUiKtx</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("androidx.navigation.ui.ktx");
        }

    }

    public static class ArouterLibraryAccessors extends SubDependencyFactory {
        private final ArouterKspLibraryAccessors laccForArouterKspLibraryAccessors = new ArouterKspLibraryAccessors(owner);

        public ArouterLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>api</b> with <b>com.alibaba:arouter-api</b> coordinates and
         * with version reference <b>arouter</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getApi() {
            return create("arouter.api");
        }

        /**
         * Dependency provider for <b>compiler</b> with <b>com.alibaba:arouter-compiler</b> coordinates and
         * with version reference <b>arouter.processor</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCompiler() {
            return create("arouter.compiler");
        }

        /**
         * Group of libraries at <b>arouter.ksp</b>
         */
        public ArouterKspLibraryAccessors getKsp() {
            return laccForArouterKspLibraryAccessors;
        }

    }

    public static class ArouterKspLibraryAccessors extends SubDependencyFactory {

        public ArouterKspLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>compiler</b> with <b>com.github.JailedBird:ArouterKspCompiler</b> coordinates and
         * with version reference <b>arouter.ksp.compiler</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCompiler() {
            return create("arouter.ksp.compiler");
        }

    }

    public static class BuglyLibraryAccessors extends SubDependencyFactory {

        public BuglyLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>com.tencent.bugly:crashreport</b> coordinates and
         * with version reference <b>bugly</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("bugly.core");
        }

    }

    public static class CoroutinesLibraryAccessors extends SubDependencyFactory {

        public CoroutinesLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>android</b> with <b>org.jetbrains.kotlinx:kotlinx-coroutines-android</b> coordinates and
         * with version reference <b>coroutines</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAndroid() {
            return create("coroutines.android");
        }

        /**
         * Dependency provider for <b>core</b> with <b>org.jetbrains.kotlinx:kotlinx-coroutines-core</b> coordinates and
         * with version reference <b>coroutines</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("coroutines.core");
        }

        /**
         * Dependency provider for <b>test</b> with <b>org.jetbrains.kotlinx:kotlinx-coroutines-test</b> coordinates and
         * with version reference <b>coroutines</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getTest() {
            return create("coroutines.test");
        }

    }

    public static class ExoplayerLibraryAccessors extends SubDependencyFactory implements DependencyNotationSupplier {

        public ExoplayerLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>exoplayer</b> with <b>com.google.android.exoplayer:exoplayer</b> coordinates and
         * with version reference <b>exoplayer</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> asProvider() {
            return create("exoplayer");
        }

        /**
         * Dependency provider for <b>ui</b> with <b>com.google.android.exoplayer:exoplayer-ui</b> coordinates and
         * with version reference <b>exoplayer</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getUi() {
            return create("exoplayer.ui");
        }

    }

    public static class GlideLibraryAccessors extends SubDependencyFactory {

        public GlideLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>compiler</b> with <b>com.github.bumptech.glide:compiler</b> coordinates and
         * with version reference <b>glide</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCompiler() {
            return create("glide.compiler");
        }

        /**
         * Dependency provider for <b>runtime</b> with <b>com.github.bumptech.glide:glide</b> coordinates and
         * with version reference <b>glide</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getRuntime() {
            return create("glide.runtime");
        }

    }

    public static class KotlinLibraryAccessors extends SubDependencyFactory {

        public KotlinLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>gradlePlugin</b> with <b>org.jetbrains.kotlin:kotlin-gradle-plugin</b> coordinates and
         * with version reference <b>kotlin</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGradlePlugin() {
            return create("kotlin.gradlePlugin");
        }

    }

    public static class KspLibraryAccessors extends SubDependencyFactory {

        public KspLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>gradlePlugin</b> with <b>com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin</b> coordinates and
         * with version reference <b>ksp</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGradlePlugin() {
            return create("ksp.gradlePlugin");
        }

    }

    public static class LifecycleLibraryAccessors extends SubDependencyFactory {
        private final LifecycleLivedataLibraryAccessors laccForLifecycleLivedataLibraryAccessors = new LifecycleLivedataLibraryAccessors(owner);
        private final LifecycleViewmodelLibraryAccessors laccForLifecycleViewmodelLibraryAccessors = new LifecycleViewmodelLibraryAccessors(owner);

        public LifecycleLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>compiler</b> with <b>androidx.lifecycle:lifecycle-compiler</b> coordinates and
         * with version reference <b>lifecycle</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCompiler() {
            return create("lifecycle.compiler");
        }

        /**
         * Dependency provider for <b>java8</b> with <b>androidx.lifecycle:lifecycle-common-java8</b> coordinates and
         * with version reference <b>lifecycle</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getJava8() {
            return create("lifecycle.java8");
        }

        /**
         * Dependency provider for <b>runtime</b> with <b>androidx.lifecycle:lifecycle-runtime</b> coordinates and
         * with version reference <b>lifecycle</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getRuntime() {
            return create("lifecycle.runtime");
        }

        /**
         * Group of libraries at <b>lifecycle.livedata</b>
         */
        public LifecycleLivedataLibraryAccessors getLivedata() {
            return laccForLifecycleLivedataLibraryAccessors;
        }

        /**
         * Group of libraries at <b>lifecycle.viewmodel</b>
         */
        public LifecycleViewmodelLibraryAccessors getViewmodel() {
            return laccForLifecycleViewmodelLibraryAccessors;
        }

    }

    public static class LifecycleLivedataLibraryAccessors extends SubDependencyFactory {

        public LifecycleLivedataLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.lifecycle:lifecycle-livedata-ktx</b> coordinates and
         * with version reference <b>lifecycle</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("lifecycle.livedata.ktx");
        }

    }

    public static class LifecycleViewmodelLibraryAccessors extends SubDependencyFactory {

        public LifecycleViewmodelLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.lifecycle:lifecycle-viewmodel-ktx</b> coordinates and
         * with version reference <b>lifecycle</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("lifecycle.viewmodel.ktx");
        }

    }

    public static class LottieLibraryAccessors extends SubDependencyFactory {

        public LottieLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>com.airbnb.android:lottie</b> coordinates and
         * with version reference <b>lottie</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("lottie.core");
        }

    }

    public static class MultidexLibraryAccessors extends SubDependencyFactory {

        public MultidexLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>com.android.support:multidex</b> coordinates and
         * with version reference <b>multidex</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("multidex.core");
        }

    }

    public static class Okhttp3LibraryAccessors extends SubDependencyFactory {

        public Okhttp3LibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>com.squareup.okhttp3:okhttp</b> coordinates and
         * with version reference <b>okhttp3</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("okhttp3.core");
        }

        /**
         * Dependency provider for <b>logging</b> with <b>com.squareup.okhttp3:logging-interceptor</b> coordinates and
         * with version reference <b>okhttp3</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLogging() {
            return create("okhttp3.logging");
        }

    }

    public static class PagingLibraryAccessors extends SubDependencyFactory {

        public PagingLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>runtime</b> with <b>androidx.paging:paging-runtime</b> coordinates and
         * with version reference <b>paging</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getRuntime() {
            return create("paging.runtime");
        }

    }

    public static class PictureLibraryAccessors extends SubDependencyFactory {

        public PictureLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>selector</b> with <b>io.github.lucksiege:pictureselector</b> coordinates and
         * with version reference <b>picture.selector</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSelector() {
            return create("picture.selector");
        }

    }

    public static class RetrofitLibraryAccessors extends SubDependencyFactory {
        private final RetrofitRetrofitLibraryAccessors laccForRetrofitRetrofitLibraryAccessors = new RetrofitRetrofitLibraryAccessors(owner);

        public RetrofitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>com.squareup.retrofit2:retrofit</b> coordinates and
         * with version reference <b>retrofit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("retrofit.core");
        }

        /**
         * Group of libraries at <b>retrofit.retrofit</b>
         */
        public RetrofitRetrofitLibraryAccessors getRetrofit() {
            return laccForRetrofitRetrofitLibraryAccessors;
        }

    }

    public static class RetrofitRetrofitLibraryAccessors extends SubDependencyFactory {
        private final RetrofitRetrofitConverterLibraryAccessors laccForRetrofitRetrofitConverterLibraryAccessors = new RetrofitRetrofitConverterLibraryAccessors(owner);

        public RetrofitRetrofitLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>retrofit.retrofit.converter</b>
         */
        public RetrofitRetrofitConverterLibraryAccessors getConverter() {
            return laccForRetrofitRetrofitConverterLibraryAccessors;
        }

    }

    public static class RetrofitRetrofitConverterLibraryAccessors extends SubDependencyFactory {

        public RetrofitRetrofitConverterLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>gson</b> with <b>com.squareup.retrofit2:converter-gson</b> coordinates and
         * with version reference <b>retrofit</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGson() {
            return create("retrofit.retrofit.converter.gson");
        }

    }

    public static class RoomLibraryAccessors extends SubDependencyFactory {

        public RoomLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>compiler</b> with <b>androidx.room:room-compiler</b> coordinates and
         * with version reference <b>room</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCompiler() {
            return create("room.compiler");
        }

        /**
         * Dependency provider for <b>gradlePlugin</b> with <b>androidx.room:room-gradle-plugin</b> coordinates and
         * with version reference <b>room</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getGradlePlugin() {
            return create("room.gradlePlugin");
        }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.room:room-ktx</b> coordinates and
         * with version reference <b>room</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("room.ktx");
        }

        /**
         * Dependency provider for <b>runtime</b> with <b>androidx.room:room-runtime</b> coordinates and
         * with version reference <b>room</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getRuntime() {
            return create("room.runtime");
        }

    }

    public static class UmengLibraryAccessors extends SubDependencyFactory {

        public UmengLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>asms</b> with <b>com.umeng.umsdk:asms</b> coordinates and
         * with version reference <b>um.asms</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getAsms() {
            return create("umeng.asms");
        }

        /**
         * Dependency provider for <b>common</b> with <b>com.umeng.umsdk:common</b> coordinates and
         * with version reference <b>um.common</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCommon() {
            return create("umeng.common");
        }

    }

    public static class VictorLibraryAccessors extends SubDependencyFactory {
        private final VictorCrashLibraryAccessors laccForVictorCrashLibraryAccessors = new VictorCrashLibraryAccessors(owner);
        private final VictorMarqueeLibraryAccessors laccForVictorMarqueeLibraryAccessors = new VictorMarqueeLibraryAccessors(owner);
        private final VictorVideoLibraryAccessors laccForVictorVideoLibraryAccessors = new VictorVideoLibraryAccessors(owner);

        public VictorLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>convenientbanner</b> with <b>com.github.Victor2018:ConvenientBanner</b> coordinates and
         * with version reference <b>convenientbanner</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getConvenientbanner() {
            return create("victor.convenientbanner");
        }

        /**
         * Dependency provider for <b>easypermissions</b> with <b>com.github.Victor2018:easypermissions</b> coordinates and
         * with version reference <b>easypermissions</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getEasypermissions() {
            return create("victor.easypermissions");
        }

        /**
         * Dependency provider for <b>khttp</b> with <b>com.github.Victor2018:KHttpLib</b> coordinates and
         * with version reference <b>khttp</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKhttp() {
            return create("victor.khttp");
        }

        /**
         * Dependency provider for <b>livedatabus</b> with <b>com.github.Victor2018:LiveDataBus</b> coordinates and
         * with version reference <b>livedatabus</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getLivedatabus() {
            return create("victor.livedatabus");
        }

        /**
         * Dependency provider for <b>match</b> with <b>com.github.Victor2018:ScreenMatchLib</b> coordinates and
         * with version reference <b>match</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getMatch() {
            return create("victor.match");
        }

        /**
         * Group of libraries at <b>victor.crash</b>
         */
        public VictorCrashLibraryAccessors getCrash() {
            return laccForVictorCrashLibraryAccessors;
        }

        /**
         * Group of libraries at <b>victor.marquee</b>
         */
        public VictorMarqueeLibraryAccessors getMarquee() {
            return laccForVictorMarqueeLibraryAccessors;
        }

        /**
         * Group of libraries at <b>victor.video</b>
         */
        public VictorVideoLibraryAccessors getVideo() {
            return laccForVictorVideoLibraryAccessors;
        }

    }

    public static class VictorCrashLibraryAccessors extends SubDependencyFactory {

        public VictorCrashLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>spider</b> with <b>com.github.Victor2018:CrashSpiderLib</b> coordinates and
         * with version reference <b>crash.spider</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSpider() {
            return create("victor.crash.spider");
        }

    }

    public static class VictorMarqueeLibraryAccessors extends SubDependencyFactory {

        public VictorMarqueeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>view</b> with <b>com.github.Victor2018:MarqueeView</b> coordinates and
         * with version reference <b>marquee.view</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getView() {
            return create("victor.marquee.view");
        }

    }

    public static class VictorVideoLibraryAccessors extends SubDependencyFactory {

        public VictorVideoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>cache</b> with <b>com.github.Victor2018:VideoCache</b> coordinates and
         * with version reference <b>video.cache</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCache() {
            return create("victor.video.cache");
        }

    }

    public static class VideoLibraryAccessors extends SubDependencyFactory {

        public VideoLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>cache</b> with <b>com.danikula:videocache</b> coordinates and
         * with version reference <b>video.cache</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCache() {
            return create("video.cache");
        }

    }

    public static class WechatLibraryAccessors extends SubDependencyFactory {

        public WechatLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>sdk</b> with <b>com.tencent.mm.opensdk:wechat-sdk-android-without-mta</b> coordinates and
         * with version reference <b>wechat.sdk</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getSdk() {
            return create("wechat.sdk");
        }

    }

    public static class WorkLibraryAccessors extends SubDependencyFactory {
        private final WorkRuntimeLibraryAccessors laccForWorkRuntimeLibraryAccessors = new WorkRuntimeLibraryAccessors(owner);

        public WorkLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Group of libraries at <b>work.runtime</b>
         */
        public WorkRuntimeLibraryAccessors getRuntime() {
            return laccForWorkRuntimeLibraryAccessors;
        }

    }

    public static class WorkRuntimeLibraryAccessors extends SubDependencyFactory {

        public WorkRuntimeLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>ktx</b> with <b>androidx.work:work-runtime-ktx</b> coordinates and
         * with version reference <b>workVersion</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getKtx() {
            return create("work.runtime.ktx");
        }

    }

    public static class ZxingLibraryAccessors extends SubDependencyFactory {

        public ZxingLibraryAccessors(AbstractExternalDependencyFactory owner) { super(owner); }

        /**
         * Dependency provider for <b>core</b> with <b>com.google.zxing:core</b> coordinates and
         * with version reference <b>zxing</b>
         * <p>
         * This dependency was declared in catalog libs.versions.toml
         */
        public Provider<MinimalExternalModuleDependency> getCore() {
            return create("zxing.core");
        }

    }

    public static class VersionAccessors extends VersionFactory  {

        private final ActivityVersionAccessors vaccForActivityVersionAccessors = new ActivityVersionAccessors(providers, config);
        private final AdsVersionAccessors vaccForAdsVersionAccessors = new AdsVersionAccessors(providers, config);
        private final ArouterVersionAccessors vaccForArouterVersionAccessors = new ArouterVersionAccessors(providers, config);
        private final CoreVersionAccessors vaccForCoreVersionAccessors = new CoreVersionAccessors(providers, config);
        private final CrashVersionAccessors vaccForCrashVersionAccessors = new CrashVersionAccessors(providers, config);
        private final FragmentVersionAccessors vaccForFragmentVersionAccessors = new FragmentVersionAccessors(providers, config);
        private final MarqueeVersionAccessors vaccForMarqueeVersionAccessors = new MarqueeVersionAccessors(providers, config);
        private final PictureVersionAccessors vaccForPictureVersionAccessors = new PictureVersionAccessors(providers, config);
        private final UmVersionAccessors vaccForUmVersionAccessors = new UmVersionAccessors(providers, config);
        private final VideoVersionAccessors vaccForVideoVersionAccessors = new VideoVersionAccessors(providers, config);
        private final WechatVersionAccessors vaccForWechatVersionAccessors = new WechatVersionAccessors(providers, config);
        public VersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>agp</b> with value <b>8.9.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAgp() { return getVersion("agp"); }

        /**
         * Version alias <b>androidDesugarJdkLibs</b> with value <b>2.1.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAndroidDesugarJdkLibs() { return getVersion("androidDesugarJdkLibs"); }

        /**
         * Version alias <b>appcompat</b> with value <b>1.6.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAppcompat() { return getVersion("appcompat"); }

        /**
         * Version alias <b>bugly</b> with value <b>latest.release</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getBugly() { return getVersion("bugly"); }

        /**
         * Version alias <b>constraintlayout</b> with value <b>2.1.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getConstraintlayout() { return getVersion("constraintlayout"); }

        /**
         * Version alias <b>convenientbanner</b> with value <b>v2.1.5</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getConvenientbanner() { return getVersion("convenientbanner"); }

        /**
         * Version alias <b>coroutines</b> with value <b>1.6.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCoroutines() { return getVersion("coroutines"); }

        /**
         * Version alias <b>easypermissions</b> with value <b>v1.1.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getEasypermissions() { return getVersion("easypermissions"); }

        /**
         * Version alias <b>espressoCore</b> with value <b>3.5.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getEspressoCore() { return getVersion("espressoCore"); }

        /**
         * Version alias <b>exoplayer</b> with value <b>2.19.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getExoplayer() { return getVersion("exoplayer"); }

        /**
         * Version alias <b>glide</b> with value <b>4.11.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getGlide() { return getVersion("glide"); }

        /**
         * Version alias <b>jetbrainsKotlinJvm</b> with value <b>2.1.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJetbrainsKotlinJvm() { return getVersion("jetbrainsKotlinJvm"); }

        /**
         * Version alias <b>junit</b> with value <b>4.13.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJunit() { return getVersion("junit"); }

        /**
         * Version alias <b>junitVersion</b> with value <b>1.1.5</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getJunitVersion() { return getVersion("junitVersion"); }

        /**
         * Version alias <b>khttp</b> with value <b>v3.6</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getKhttp() { return getVersion("khttp"); }

        /**
         * Version alias <b>kotlin</b> with value <b>2.1.10</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getKotlin() { return getVersion("kotlin"); }

        /**
         * Version alias <b>ksp</b> with value <b>2.1.10-1.0.31</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getKsp() { return getVersion("ksp"); }

        /**
         * Version alias <b>lifecycle</b> with value <b>2.4.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getLifecycle() { return getVersion("lifecycle"); }

        /**
         * Version alias <b>livedatabus</b> with value <b>v2.1.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getLivedatabus() { return getVersion("livedatabus"); }

        /**
         * Version alias <b>lottie</b> with value <b>5.2.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getLottie() { return getVersion("lottie"); }

        /**
         * Version alias <b>match</b> with value <b>v1.5</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getMatch() { return getVersion("match"); }

        /**
         * Version alias <b>material</b> with value <b>1.10.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getMaterial() { return getVersion("material"); }

        /**
         * Version alias <b>multidex</b> with value <b>1.0.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getMultidex() { return getVersion("multidex"); }

        /**
         * Version alias <b>navigationFragmentKtx</b> with value <b>2.6.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getNavigationFragmentKtx() { return getVersion("navigationFragmentKtx"); }

        /**
         * Version alias <b>navigationUiKtx</b> with value <b>2.6.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getNavigationUiKtx() { return getVersion("navigationUiKtx"); }

        /**
         * Version alias <b>okhttp3</b> with value <b>4.2.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getOkhttp3() { return getVersion("okhttp3"); }

        /**
         * Version alias <b>paging</b> with value <b>3.0.0-alpha02</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getPaging() { return getVersion("paging"); }

        /**
         * Version alias <b>pangrowth</b> with value <b>2.9.0.4</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getPangrowth() { return getVersion("pangrowth"); }

        /**
         * Version alias <b>retrofit</b> with value <b>2.9.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getRetrofit() { return getVersion("retrofit"); }

        /**
         * Version alias <b>room</b> with value <b>2.6.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getRoom() { return getVersion("room"); }

        /**
         * Version alias <b>swiperefreshlayout</b> with value <b>1.1.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSwiperefreshlayout() { return getVersion("swiperefreshlayout"); }

        /**
         * Version alias <b>workVersion</b> with value <b>2.7.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getWorkVersion() { return getVersion("workVersion"); }

        /**
         * Version alias <b>zxing</b> with value <b>3.5.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getZxing() { return getVersion("zxing"); }

        /**
         * Group of versions at <b>versions.activity</b>
         */
        public ActivityVersionAccessors getActivity() {
            return vaccForActivityVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.ads</b>
         */
        public AdsVersionAccessors getAds() {
            return vaccForAdsVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.arouter</b>
         */
        public ArouterVersionAccessors getArouter() {
            return vaccForArouterVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.core</b>
         */
        public CoreVersionAccessors getCore() {
            return vaccForCoreVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.crash</b>
         */
        public CrashVersionAccessors getCrash() {
            return vaccForCrashVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.fragment</b>
         */
        public FragmentVersionAccessors getFragment() {
            return vaccForFragmentVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.marquee</b>
         */
        public MarqueeVersionAccessors getMarquee() {
            return vaccForMarqueeVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.picture</b>
         */
        public PictureVersionAccessors getPicture() {
            return vaccForPictureVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.um</b>
         */
        public UmVersionAccessors getUm() {
            return vaccForUmVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.video</b>
         */
        public VideoVersionAccessors getVideo() {
            return vaccForVideoVersionAccessors;
        }

        /**
         * Group of versions at <b>versions.wechat</b>
         */
        public WechatVersionAccessors getWechat() {
            return vaccForWechatVersionAccessors;
        }

    }

    public static class ActivityVersionAccessors extends VersionFactory  {

        public ActivityVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>activity.ktx</b> with value <b>1.4.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getKtx() { return getVersion("activity.ktx"); }

    }

    public static class AdsVersionAccessors extends VersionFactory  {

        private final AdsSdkVersionAccessors vaccForAdsSdkVersionAccessors = new AdsSdkVersionAccessors(providers, config);
        public AdsVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of versions at <b>versions.ads.sdk</b>
         */
        public AdsSdkVersionAccessors getSdk() {
            return vaccForAdsSdkVersionAccessors;
        }

    }

    public static class AdsSdkVersionAccessors extends VersionFactory  {

        public AdsSdkVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>ads.sdk.pro</b> with value <b>6.7.1.6</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getPro() { return getVersion("ads.sdk.pro"); }

    }

    public static class ArouterVersionAccessors extends VersionFactory  implements VersionNotationSupplier {

        private final ArouterKspVersionAccessors vaccForArouterKspVersionAccessors = new ArouterKspVersionAccessors(providers, config);
        public ArouterVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>arouter</b> with value <b>1.5.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> asProvider() { return getVersion("arouter"); }

        /**
         * Version alias <b>arouter.processor</b> with value <b>1.5.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getProcessor() { return getVersion("arouter.processor"); }

        /**
         * Group of versions at <b>versions.arouter.ksp</b>
         */
        public ArouterKspVersionAccessors getKsp() {
            return vaccForArouterKspVersionAccessors;
        }

    }

    public static class ArouterKspVersionAccessors extends VersionFactory  {

        public ArouterKspVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>arouter.ksp.compiler</b> with value <b>1.6.10-1.0.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCompiler() { return getVersion("arouter.ksp.compiler"); }

    }

    public static class CoreVersionAccessors extends VersionFactory  {

        public CoreVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>core.ktx</b> with value <b>1.10.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getKtx() { return getVersion("core.ktx"); }

    }

    public static class CrashVersionAccessors extends VersionFactory  {

        public CrashVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>crash.spider</b> with value <b>v1.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSpider() { return getVersion("crash.spider"); }

    }

    public static class FragmentVersionAccessors extends VersionFactory  {

        public FragmentVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>fragment.ktx</b> with value <b>1.4.1</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getKtx() { return getVersion("fragment.ktx"); }

    }

    public static class MarqueeVersionAccessors extends VersionFactory  {

        public MarqueeVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>marquee.view</b> with value <b>v1.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getView() { return getVersion("marquee.view"); }

    }

    public static class PictureVersionAccessors extends VersionFactory  {

        public PictureVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>picture.selector</b> with value <b>v3.11.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSelector() { return getVersion("picture.selector"); }

    }

    public static class UmVersionAccessors extends VersionFactory  {

        public UmVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>um.asms</b> with value <b>1.6.3</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getAsms() { return getVersion("um.asms"); }

        /**
         * Version alias <b>um.common</b> with value <b>9.5.2</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCommon() { return getVersion("um.common"); }

    }

    public static class VideoVersionAccessors extends VersionFactory  {

        public VideoVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>video.cache</b> with value <b>v1.1.0</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getCache() { return getVersion("video.cache"); }

    }

    public static class WechatVersionAccessors extends VersionFactory  {

        public WechatVersionAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Version alias <b>wechat.sdk</b> with value <b>6.7.9</b>
         * <p>
         * If the version is a rich version and cannot be represented as a
         * single version string, an empty string is returned.
         * <p>
         * This version was declared in catalog libs.versions.toml
         */
        public Provider<String> getSdk() { return getVersion("wechat.sdk"); }

    }

    public static class BundleAccessors extends BundleFactory {

        public BundleAccessors(ObjectFactory objects, ProviderFactory providers, DefaultVersionCatalog config, ImmutableAttributesFactory attributesFactory, CapabilityNotationParser capabilityNotationParser) { super(objects, providers, config, attributesFactory, capabilityNotationParser); }

        /**
         * Dependency bundle provider for <b>coroutines</b> which contains the following dependencies:
         * <ul>
         *    <li>org.jetbrains.kotlinx:kotlinx-coroutines-core</li>
         *    <li>org.jetbrains.kotlinx:kotlinx-coroutines-android</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getCoroutines() {
            return createBundle("coroutines");
        }

        /**
         * Dependency bundle provider for <b>exoplayer</b> which contains the following dependencies:
         * <ul>
         *    <li>com.google.android.exoplayer:exoplayer</li>
         *    <li>com.google.android.exoplayer:exoplayer-ui</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getExoplayer() {
            return createBundle("exoplayer");
        }

        /**
         * Dependency bundle provider for <b>ktx</b> which contains the following dependencies:
         * <ul>
         *    <li>androidx.core:core-ktx</li>
         *    <li>androidx.activity:activity-ktx</li>
         *    <li>androidx.fragment:fragment-ktx</li>
         *    <li>androidx.work:work-runtime-ktx</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getKtx() {
            return createBundle("ktx");
        }

        /**
         * Dependency bundle provider for <b>lifecycle</b> which contains the following dependencies:
         * <ul>
         *    <li>androidx.lifecycle:lifecycle-runtime</li>
         *    <li>androidx.lifecycle:lifecycle-common-java8</li>
         *    <li>androidx.lifecycle:lifecycle-compiler</li>
         *    <li>androidx.lifecycle:lifecycle-viewmodel-ktx</li>
         *    <li>androidx.lifecycle:lifecycle-livedata-ktx</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getLifecycle() {
            return createBundle("lifecycle");
        }

        /**
         * Dependency bundle provider for <b>okhttp3</b> which contains the following dependencies:
         * <ul>
         *    <li>com.squareup.okhttp3:okhttp</li>
         *    <li>com.squareup.okhttp3:logging-interceptor</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getOkhttp3() {
            return createBundle("okhttp3");
        }

        /**
         * Dependency bundle provider for <b>retrofit</b> which contains the following dependencies:
         * <ul>
         *    <li>com.squareup.retrofit2:retrofit</li>
         *    <li>com.squareup.retrofit2:converter-gson</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getRetrofit() {
            return createBundle("retrofit");
        }

        /**
         * Dependency bundle provider for <b>umeng</b> which contains the following dependencies:
         * <ul>
         *    <li>com.umeng.umsdk:common</li>
         *    <li>com.umeng.umsdk:asms</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getUmeng() {
            return createBundle("umeng");
        }

        /**
         * Dependency bundle provider for <b>victor</b> which contains the following dependencies:
         * <ul>
         *    <li>com.github.Victor2018:easypermissions</li>
         *    <li>com.github.Victor2018:LiveDataBus</li>
         *    <li>com.github.Victor2018:ScreenMatchLib</li>
         *    <li>com.github.Victor2018:CrashSpiderLib</li>
         *    <li>com.github.Victor2018:KHttpLib</li>
         *    <li>com.github.Victor2018:VideoCache</li>
         *    <li>com.github.Victor2018:MarqueeView</li>
         *    <li>com.github.Victor2018:ConvenientBanner</li>
         * </ul>
         * <p>
         * This bundle was declared in catalog libs.versions.toml
         */
        public Provider<ExternalModuleDependencyBundle> getVictor() {
            return createBundle("victor");
        }

    }

    public static class PluginAccessors extends PluginFactory {
        private final AndroidPluginAccessors paccForAndroidPluginAccessors = new AndroidPluginAccessors(providers, config);
        private final AndroidxPluginAccessors paccForAndroidxPluginAccessors = new AndroidxPluginAccessors(providers, config);
        private final JetbrainsPluginAccessors paccForJetbrainsPluginAccessors = new JetbrainsPluginAccessors(providers, config);
        private final KotlinPluginAccessors paccForKotlinPluginAccessors = new KotlinPluginAccessors(providers, config);
        private final VictorPluginAccessors paccForVictorPluginAccessors = new VictorPluginAccessors(providers, config);

        public PluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of plugins at <b>plugins.android</b>
         */
        public AndroidPluginAccessors getAndroid() {
            return paccForAndroidPluginAccessors;
        }

        /**
         * Group of plugins at <b>plugins.androidx</b>
         */
        public AndroidxPluginAccessors getAndroidx() {
            return paccForAndroidxPluginAccessors;
        }

        /**
         * Group of plugins at <b>plugins.jetbrains</b>
         */
        public JetbrainsPluginAccessors getJetbrains() {
            return paccForJetbrainsPluginAccessors;
        }

        /**
         * Group of plugins at <b>plugins.kotlin</b>
         */
        public KotlinPluginAccessors getKotlin() {
            return paccForKotlinPluginAccessors;
        }

        /**
         * Group of plugins at <b>plugins.victor</b>
         */
        public VictorPluginAccessors getVictor() {
            return paccForVictorPluginAccessors;
        }

    }

    public static class AndroidPluginAccessors extends PluginFactory {

        public AndroidPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>android.application</b> with plugin id <b>com.android.application</b> and
         * with version reference <b>agp</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getApplication() { return createPlugin("android.application"); }

        /**
         * Plugin provider for <b>android.library</b> with plugin id <b>com.android.library</b> and
         * with version reference <b>agp</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getLibrary() { return createPlugin("android.library"); }

    }

    public static class AndroidxPluginAccessors extends PluginFactory {

        public AndroidxPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>androidx.room</b> with plugin id <b>androidx.room</b> and
         * with version reference <b>room</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getRoom() { return createPlugin("androidx.room"); }

    }

    public static class JetbrainsPluginAccessors extends PluginFactory {
        private final JetbrainsKotlinPluginAccessors paccForJetbrainsKotlinPluginAccessors = new JetbrainsKotlinPluginAccessors(providers, config);

        public JetbrainsPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of plugins at <b>plugins.jetbrains.kotlin</b>
         */
        public JetbrainsKotlinPluginAccessors getKotlin() {
            return paccForJetbrainsKotlinPluginAccessors;
        }

    }

    public static class JetbrainsKotlinPluginAccessors extends PluginFactory {

        public JetbrainsKotlinPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>jetbrains.kotlin.jvm</b> with plugin id <b>org.jetbrains.kotlin.jvm</b> and
         * with version reference <b>jetbrainsKotlinJvm</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getJvm() { return createPlugin("jetbrains.kotlin.jvm"); }

        /**
         * Plugin provider for <b>jetbrains.kotlin.ksp</b> with plugin id <b>com.google.devtools.ksp</b> and
         * with version reference <b>ksp</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getKsp() { return createPlugin("jetbrains.kotlin.ksp"); }

    }

    public static class KotlinPluginAccessors extends PluginFactory {

        public KotlinPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>kotlin.android</b> with plugin id <b>org.jetbrains.kotlin.android</b> and
         * with version reference <b>kotlin</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getAndroid() { return createPlugin("kotlin.android"); }

    }

    public static class VictorPluginAccessors extends PluginFactory {
        private final VictorAndroidPluginAccessors paccForVictorAndroidPluginAccessors = new VictorAndroidPluginAccessors(providers, config);
        private final VictorJvmPluginAccessors paccForVictorJvmPluginAccessors = new VictorJvmPluginAccessors(providers, config);
        private final VictorOpenPluginAccessors paccForVictorOpenPluginAccessors = new VictorOpenPluginAccessors(providers, config);

        public VictorPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Group of plugins at <b>plugins.victor.android</b>
         */
        public VictorAndroidPluginAccessors getAndroid() {
            return paccForVictorAndroidPluginAccessors;
        }

        /**
         * Group of plugins at <b>plugins.victor.jvm</b>
         */
        public VictorJvmPluginAccessors getJvm() {
            return paccForVictorJvmPluginAccessors;
        }

        /**
         * Group of plugins at <b>plugins.victor.open</b>
         */
        public VictorOpenPluginAccessors getOpen() {
            return paccForVictorOpenPluginAccessors;
        }

    }

    public static class VictorAndroidPluginAccessors extends PluginFactory {

        public VictorAndroidPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>victor.android.application</b> with plugin id <b>com.victor.convention.application</b> and
         * with <b>no version specified</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getApplication() { return createPlugin("victor.android.application"); }

        /**
         * Plugin provider for <b>victor.android.base</b> with plugin id <b>com.victor.convention.base</b> and
         * with <b>no version specified</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getBase() { return createPlugin("victor.android.base"); }

        /**
         * Plugin provider for <b>victor.android.lib</b> with plugin id <b>com.victor.convention.lib</b> and
         * with <b>no version specified</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getLib() { return createPlugin("victor.android.lib"); }

        /**
         * Plugin provider for <b>victor.android.lint</b> with plugin id <b>com.victor.convention.lint</b> and
         * with <b>no version specified</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getLint() { return createPlugin("victor.android.lint"); }

        /**
         * Plugin provider for <b>victor.android.module</b> with plugin id <b>com.victor.convention.module</b> and
         * with <b>no version specified</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getModule() { return createPlugin("victor.android.module"); }

        /**
         * Plugin provider for <b>victor.android.room</b> with plugin id <b>com.victor.convention.android.room</b> and
         * with <b>no version specified</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getRoom() { return createPlugin("victor.android.room"); }

    }

    public static class VictorJvmPluginAccessors extends PluginFactory {

        public VictorJvmPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>victor.jvm.library</b> with plugin id <b>com.victor.convention.jvm.library</b> and
         * with <b>no version specified</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getLibrary() { return createPlugin("victor.jvm.library"); }

    }

    public static class VictorOpenPluginAccessors extends PluginFactory {

        public VictorOpenPluginAccessors(ProviderFactory providers, DefaultVersionCatalog config) { super(providers, config); }

        /**
         * Plugin provider for <b>victor.open.source</b> with plugin id <b>com.victor.convention.open.source</b> and
         * with <b>no version specified</b>
         * <p>
         * This plugin was declared in catalog libs.versions.toml
         */
        public Provider<PluginDependency> getSource() { return createPlugin("victor.open.source"); }

    }

}
