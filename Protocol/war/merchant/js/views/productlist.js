directory.ProductListView = Backbone.View.extend({
   
	events : {
		"mouseenter .left" : this.prevSlide,
		"mouseenter .right" : this.nextSlide,
	},
	
	nextSlide:function()
	{
		this.$el.carousel('next');
	},
	
	prevSlide:function()
	{
		this.$el.carousel('prev');
	},
	
    initialize:function () {
    	_.bindAll(this);
    	this.model.on("reset", this.render, this);
        this.model.on("add", function (product) {
        	this.$('.carousel-inner').append(new directory.ProductListItemView({model:product}).render().el);
        }, this);
    },

	render:function () {
		
		this.$el.html(this.template(this.model.toJSON()));
	    
	    this.$slides = $('.carousel-inner');
	    _.each(this.model.models, function (product) {
	    	this.$slides.append(new directory.ProductListItemView({model:product}).render().el);
	    }, this);
	    
	    this.$("#productsList").carousel();
	    
	    return this;
	}
});

directory.ProductListItemView = Backbone.View.extend({
    className: "item",
	
	events:{
    	'click' : function(element) {
    			Backbone.history.navigate("#products/" + this.model.id, {trigger: true});
    		}
    },
    
    initialize:function () {
    	_.bindAll(this);
        this.model.on("destroy", this.close, this);
        this.model.on("sync", this.sync, this);
    },
    
    sync:function()
    {
    	this.render();
    	Backbone.history.navigate('#products/' + this.model.id);
    },
    
    render:function () {
    	
    	if(this.model.collection.indexOf(this.model) == 0)
    		this.$el.addClass("active");
    	
    	var checkedModel = _.extend(this.model.toJSON(), { cert : this.model.get('cert').replace(' ', '') });
    	
        this.$el.html(this.template(checkedModel));
        return this;
    }

});