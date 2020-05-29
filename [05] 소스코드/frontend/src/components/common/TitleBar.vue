<template>
	<div id="titlebar" class="gradient">
		<div class="title-bar-inner">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h2>{{title}}</h2>
						<span>{{subtitle}}</span>
					</div>
				</div>
			</div>
		</div>
		<div class="title-overlay"></div>
	</div>
</template>

<script>

export default {
	props: ['title', 'subtitle']
};
</script>
